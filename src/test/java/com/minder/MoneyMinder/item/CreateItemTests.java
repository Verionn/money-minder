package com.minder.MoneyMinder.item;

import com.minder.MoneyMinder.MoneyMinderApplicationTests;
import com.minder.MoneyMinder.controllers.item.dto.CreateItemRequestBody;
import com.minder.MoneyMinder.controllers.item.dto.ItemListResponse;
import com.minder.MoneyMinder.controllers.item.dto.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateItemTests extends MoneyMinderApplicationTests {

    @Test
    @DisplayName("Should post item and return created")
    public void shouldPostItemAndReturnCreated() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = createValidItemRequestBody(FIRST_ITEM_NAME);

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertNotNull(addItemResponse.getBody());
        assertThat(addItemResponse.getBody().name(), equalTo(FIRST_ITEM_NAME));
        assertThat(addItemResponse.getBody().price(), equalTo(VALID_PRICE));
        assertThat(addItemResponse.getBody().amount(), equalTo(VALID_AMOUNT));
        assertThat(addItemResponse.getBody().weight(), equalTo(VALID_WEIGHT));
    }

    @Test
    @DisplayName("Should not post item when given bad list id wrong return not found")
    public void shouldNotPostItemAndReturnNotFoundWhenGivenWrongListId() {
        runAsUser();

        //given
        var createdItemRequestBody = createValidItemRequestBody(FIRST_ITEM_NAME);

        //when
        var addItemResponse = client.postForEntity(itemsPath(INVALID_LIST_ID),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should not post item when item name is blank and return bad request")
    public void shouldNotPostItemAndReturnBadRequestWhenGivenWrongItemName() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = createValidItemRequestBody(INVALID_ITEM_NAME);

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should not post item when item price is below 0 and return bad request")
    public void shouldNotPostItemAndReturnBadRequestWhenGivenWrongItemPrice() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = new CreateItemRequestBody(FIRST_ITEM_NAME,
                INVALID_PRICE,
                VALID_AMOUNT,
                VALID_CATEGORY_ID,
                VALID_WEIGHT,
                LocalDateTime.now());

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should not post item when item weight is below 0 and return bad request")
    public void shouldNotPostItemAndReturnBadRequestWhenGivenWrongItemWeight() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = new CreateItemRequestBody(FIRST_ITEM_NAME,
                VALID_PRICE,
                VALID_AMOUNT,
                VALID_CATEGORY_ID,
                INVALID_WEIGHT,
                LocalDateTime.now());

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should not post item when item amount is below 0 and return bad request")
    public void shouldNotPostItemAndReturnBadRequestWhenGivenWrongItemAmount() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = new CreateItemRequestBody(FIRST_ITEM_NAME,
                VALID_PRICE,
                INVALID_AMOUNT,
                VALID_CATEGORY_ID,
                VALID_WEIGHT,
                LocalDateTime.now());

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should not post item when item category is blank and return bad request")
    public void shouldNotPostItemAndReturnBadRequestWhenGivenWrongItemCategory() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdItemRequestBody = new CreateItemRequestBody(FIRST_ITEM_NAME,
                VALID_PRICE,
                VALID_AMOUNT,
                INVALID_CATEGORY_ID,
                VALID_WEIGHT,
                LocalDateTime.now());

        //when
        var addItemResponse = client.postForEntity(itemsPath(createdList.listId()),
                createdItemRequestBody, ItemResponse.class);

        //then
        assertThat(addItemResponse.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertNull(addItemResponse.getBody());
    }

    @Test
    @DisplayName("Should get items and return OK")
    public void shouldGetItemsAndReturnOK() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdCategory = createCategory(FIRST_CATEGORY_NAME);
        addItem(FIRST_ITEM_NAME, createdList.listId(), createdCategory.categoryId());
        addItem(SECOND_ITEM_NAME, createdList.listId(), createdCategory.categoryId());

        //when
        var getItemsResponse = client.getForEntity(itemsPath(createdList.listId()),
                ItemListResponse.class);

        //then
        assertThat(getItemsResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(getItemsResponse.getBody());
        assertThat(getItemsResponse.getBody().items().size(), equalTo(2));
    }

    @Test
    @DisplayName("Should return not found when given wrong list id")
    public void shouldReturnNotFoundWhenGivenWrongListId() {
        runAsUser();

        //when
        var getItemsResponse = client.getForEntity(itemsPath(INVALID_LIST_ID), ItemListResponse.class);

        //then
        assertThat(getItemsResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        assertNull(getItemsResponse.getBody());
    }

    @Test
    @DisplayName("Should return specific item from specific list and status 200")
    public void ShouldReturnSpecificItemFromSpecficiListAndOk() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdCategory = createCategory(FIRST_CATEGORY_NAME);
        var addedItem = addItem(FIRST_ITEM_NAME, createdList.listId(), createdCategory.categoryId());

        //when
        var getSpecificItemResponse = client.getForEntity(itemsPath(createdList.listId(),
                addedItem.itemId()), ItemResponse.class);

        //then
        assertThat(getSpecificItemResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(getSpecificItemResponse.getBody());
        assertThat(getSpecificItemResponse.getBody().name(), equalTo(FIRST_ITEM_NAME));
        assertThat(getSpecificItemResponse.getBody().price(), equalTo(VALID_PRICE));
        assertThat(getSpecificItemResponse.getBody().weight(), equalTo(VALID_WEIGHT));
        assertThat(getSpecificItemResponse.getBody().amount(), equalTo(VALID_AMOUNT));
        assertThat(getSpecificItemResponse.getBody().categoryId(), equalTo(createdCategory.categoryId()));
    }

    @Test
    @DisplayName("Should not get specific item when given bad listId")
    public void ShouldNotGetItemWhenGivenBadListId() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);
        var createdCategory = createCategory(FIRST_CATEGORY_NAME);
        var addedItem = addItem(FIRST_ITEM_NAME, createdList.listId(), createdCategory.categoryId());

        //when
        var getSpecificItemResponse = client.getForEntity(itemsPath(INVALID_LIST_ID,
                addedItem.itemId()), ItemResponse.class);

        //then
        assertThat(getSpecificItemResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Should not get specific item when given bad itemId")
    public void ShouldNotGetItemWhenGivenBadItemId() {
        runAsUser();

        //given
        var createdList = createList(FIRST_LIST_NAME);

        //when
        var getSpecificItemResponse = client.getForEntity(itemsPath(createdList.listId(),
                INVALID_ITEM_ID), ItemResponse.class);

        //then
        assertThat(getSpecificItemResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
