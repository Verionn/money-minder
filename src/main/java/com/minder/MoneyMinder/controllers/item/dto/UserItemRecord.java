package com.minder.MoneyMinder.controllers.item.dto;

import java.time.LocalDateTime;

public record UserItemRecord(Long itemId,
                             Long listId,
                             Long categoryId,
                             String name,
                             Double price,
                             Integer amount,
                             Long weight,
                             LocalDateTime timeCreated) {
}
