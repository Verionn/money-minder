package com.minder.MoneyMinder.controllers.item;

import com.minder.MoneyMinder.controllers.item.dto.*;
import com.minder.MoneyMinder.models.ItemEntity;
import com.minder.MoneyMinder.services.*;
import com.minder.MoneyMinder.services.implementations.ItemServiceImpl;
import com.minder.MoneyMinder.services.implementations.ListServiceImpl;
import com.minder.MoneyMinder.services.mappers.ItemMapper;
import com.minder.MoneyMinder.services.mappers.UserItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping(path = "/lists")
public class ItemController {
    private final ItemService itemService;
    private final ListService listService;
    private final UserItemService userItemService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;
    private final UserItemMapper userItemMapper = UserItemMapper.INSTANCE;

    @Autowired
    public ItemController(ItemServiceImpl itemService, ListServiceImpl listService, UserItemService userItemService) {
        this.itemService = itemService;
        this.listService = listService;
        this.userItemService = userItemService;
    }

    @GetMapping("/{listId}/items")
    public ResponseEntity<ItemListResponse> getItemsFromSpecificList(@PathVariable Long listId) {
        if (!checkIfListExists(listId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ItemListResponse(
                itemMapper.itemsToItemResponses(itemService.getItemsOnSpecificList(listId))));
    }

    @GetMapping("/{listId}/items/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long listId, @PathVariable Long itemId) {
        if (!checkIfListExists(listId)) {
            return ResponseEntity.notFound().build();
        }

        return itemService.getItem(itemId).map(itemRecord -> ResponseEntity.ok().body(
                        itemMapper.itemToItemResponse(itemRecord)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{listId}/items")
    public ResponseEntity<ItemResponse> addItem(@PathVariable Long listId, @RequestBody CreateItemRequestBody createItemRequestBody) {
        if (!checkIfListExists(listId)) {
            return ResponseEntity.notFound().build();
        }

        if (checkIfNewItemRequestBodyIsInvalid(createItemRequestBody)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(itemMapper.itemToItemResponse(
                itemService.addItem(itemMapper.
                        createItemRequestBodyToItem(createItemRequestBody), listId))
        );
    }

    @DeleteMapping(path = "{listId}/items/{itemId}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long listId, @PathVariable Long itemId) {
        if (!checkIfItemAndListExists(listId, itemId)) {
            return ResponseEntity.notFound().build();
        }

        itemService.deleteItem(itemId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{listId}/items/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long listId,
                                                   @PathVariable Long itemId,
                                                   @RequestBody UpdateItemRequestBody updateItemRequestBody) {

        if (!checkIfItemAndListExists(itemId, listId)) {
            return ResponseEntity.notFound().build();
        }

        if (!checkIfListExists(updateItemRequestBody.listId())) {
            return ResponseEntity.notFound().build();
        }

        if (checkIfUpdateItemRequestBodyIsInvalid(updateItemRequestBody)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(itemMapper.itemToItemResponse(itemService.
                updateItem(itemId, updateItemRequestBody)));
    }

    @PostMapping(path = "/{listId}/items/{itemId}/bought")
    public ResponseEntity<UserItemResponse>markItemAsBought(@PathVariable Long listId,
                                                            @PathVariable Long itemId){
        if(!checkIfListExists(listId)){
            return ResponseEntity.notFound().build();
        }

        Optional<ItemEntity> itemEntity = itemService.getItem(itemId);

        if (itemEntity.isPresent()) {
            itemService.deleteItem(itemId);
            return ResponseEntity.ok().body(userItemService.markItemAsBought(userItemMapper.itemEntityToUserItemRecord(itemEntity.get(), LocalDateTime.now())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean checkIfNewItemRequestBodyIsInvalid(CreateItemRequestBody createItemRequestBody) {
        return createItemRequestBody.amount() < 0 || createItemRequestBody.name().isBlank() || createItemRequestBody.categoryId() <= 0 || createItemRequestBody.price() < 0 || createItemRequestBody.weight() < 0;
    }

    private boolean checkIfUpdateItemRequestBodyIsInvalid(UpdateItemRequestBody updateItemRequestBody) {
        return updateItemRequestBody.amount() < 0 || updateItemRequestBody.name().isBlank() || updateItemRequestBody.categoryId() <= 0 || updateItemRequestBody.price() < 0 || updateItemRequestBody.weight() < 0;
    }

    private boolean checkIfItemAndListExists(Long itemId, Long listId) {
        return listService.existsById(listId) && itemService.existsById(itemId);
    }

    private boolean checkIfListExists(Long listId) {
        return listService.existsById(listId);
    }
}
