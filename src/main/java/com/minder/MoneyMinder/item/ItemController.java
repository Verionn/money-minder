package com.minder.MoneyMinder.item;

import com.minder.MoneyMinder.item.dto.CreateItemRequestBody;
import com.minder.MoneyMinder.item.dto.ItemListResponse;
import com.minder.MoneyMinder.item.dto.ItemResponse;
import com.minder.MoneyMinder.item.dto.UpdateItemRequestBody;
import com.minder.MoneyMinder.list.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/lists")
public class ItemController {
    private final ItemService itemService;
    private final ListService listService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @Autowired
    public ItemController(ItemService itemService, ListService listService) {
        this.itemService = itemService;
        this.listService = listService;
    }

    @GetMapping("/{listId}/items")
    public ResponseEntity<ItemListResponse> getItemsFromSpecificList(@PathVariable Long listId) {
        if (!listService.existsById(listId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ItemListResponse(
                itemMapper.itemsToItemResponses(itemService.getItemsOnSpecificList(listId))));
    }

    @GetMapping("/{listId}/items/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long listId, @PathVariable Long itemId) {
        return itemService.getItem(itemId, listId).map(itemRecord -> ResponseEntity.ok().body(
                        itemMapper.itemToItemResponse(itemRecord)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{listId}/items")
    public ResponseEntity<ItemResponse> addItem(@PathVariable Long listId, @RequestBody CreateItemRequestBody createItemRequestBody) {
        //TODO:
        //czy lista istnieje

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
        //TODO::
//        if(checkIfListAndItemExists(listId, shoppingItemId)){
//            return ResponseEntity.notFound().build();
//        }
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{listId}/items/{itemId}")
    public ResponseEntity<ItemResponse> updateItems(@PathVariable Long listId,
                                                    @PathVariable Long itemId,
                                                    @RequestBody UpdateItemRequestBody updateItemRequestBody) {

        //TODO:
        //weryfikacja czy lista oraz item istnieje

        return ResponseEntity.ok().body(itemMapper.itemToItemResponse(itemService.
                updateItem(itemId, updateItemRequestBody)));
    }

    private boolean checkIfNewItemRequestBodyIsInvalid(CreateItemRequestBody createItemRequestBody) {
        return createItemRequestBody.amount() < 0 || createItemRequestBody.name().isBlank() || createItemRequestBody.category().isBlank() || createItemRequestBody.price() < 0;
    }
}