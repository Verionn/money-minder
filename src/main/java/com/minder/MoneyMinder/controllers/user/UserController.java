package com.minder.MoneyMinder.controllers.user;

import com.minder.MoneyMinder.controllers.user.dto.*;
import com.minder.MoneyMinder.models.ResetPasswordTokenEntity;
import com.minder.MoneyMinder.services.UserService;
import com.minder.MoneyMinder.services.mappers.UserMapper;

import java.time.Duration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final int EXPIRATION_TIME_IN_MINUTES = 10;
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserRequest registerUserRequest) {

        if (checkIfRegisterUserRequestIsInvalid(registerUserRequest)) {
            return ResponseEntity.badRequest().build();
        }

        if (checkIfEmailIsRegistered(registerUserRequest.email())) {
            return ResponseEntity.status(CONFLICT).build();
        }

        return ResponseEntity.ok(userService.register(registerUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        if (checkIfLoginRequestIsInvalid(loginRequest)) {
            return ResponseEntity.badRequest().build();
        }

        if (!checkIfEmailIsRegistered(loginRequest.email())) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        return userService.login(loginRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("/changePassword")
    public ResponseEntity<LoginResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

        var user = userService.getUserByEmail();
        if (user.isRight()) {
            return ResponseEntity.notFound().build();
        }

        if (checkIfChangePasswordRequestIsInvalid(changePasswordRequest)) {
            return ResponseEntity.badRequest().build();
        }

        return userService.changePassword(changePasswordRequest, user.getLeft())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(FORBIDDEN).build());
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<HttpStatus> resetPassword(HttpServletRequest request, @RequestBody ResetPasswordRequest resetPasswordRequest) {

        if (checkIfResetPasswordRequestIsInvalid(resetPasswordRequest)) {
            return ResponseEntity.badRequest().build();
        }

        String username = userService.getNameByEmail(resetPasswordRequest.email());
        if (username == null) {
            return ResponseEntity.notFound().build();
        }

        userService.resetPassword(userMapper.resetPasswordRequestToResetPasswordTokenEntity(resetPasswordRequest, username), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/confirmResetPassword")
    public ResponseEntity<HttpStatus> confirmResetPassword(
            @RequestParam("token") String token,
            @RequestBody ConfirmResetPasswordRequest confirmResetPasswordRequest) {

        if (checkIfTokenIsInvalid(token)) {
            return ResponseEntity.notFound().build();
        }

        if (checkIfTokenIsExpired(token)) {
            userService.removeToken(token);
            return ResponseEntity.notFound().build();
        }

        if (confirmResetPasswordRequest.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return userService.confirmResetPassword(token, confirmResetPasswordRequest);
    }

    private boolean checkIfTokenIsExpired(String token) {
        ResetPasswordTokenEntity resetPasswordTokenEntity = userService.getResetPasswordTokenEntityByToken(token);

        LocalDateTime expirationDate = resetPasswordTokenEntity.getExpirationDate();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(expirationDate, now);
        return duration.getSeconds() > EXPIRATION_TIME_IN_MINUTES * 60;
    }

    private boolean checkIfTokenIsInvalid(String token) {
        if (token.isBlank()) {
            return true;
        }
        return !userService.checkIfTokenExists(token);
    }

    private boolean checkIfChangePasswordRequestIsInvalid(ChangePasswordRequest changePasswordRequest) {
        return changePasswordRequest.newPassword().isBlank()
                || changePasswordRequest.oldPassword().isBlank()
                || changePasswordRequest.repeatOldPassword().isBlank()
                || !Objects.equals(changePasswordRequest.oldPassword(), changePasswordRequest.repeatOldPassword())
                || changePasswordRequest.oldPassword().equals(changePasswordRequest.newPassword());
    }

    private boolean checkIfRegisterUserRequestIsInvalid(RegisterUserRequest registerUserRequest) {
        return registerUserRequest.email().isBlank()
                || registerUserRequest.password().isBlank()
                || registerUserRequest.name().isBlank();
    }

    private boolean checkIfResetPasswordRequestIsInvalid(ResetPasswordRequest resetPasswordRequest) {
        return resetPasswordRequest.email().isBlank()
                || !resetPasswordRequest.email().contains("@");
    }

    private boolean checkIfLoginRequestIsInvalid(LoginRequest loginRequest) {
        return loginRequest.email().isBlank() || loginRequest.password().isBlank();
    }

    private boolean checkIfEmailIsRegistered(String email) {
        return userService.checkIfEmailExists(email);
    }
}
