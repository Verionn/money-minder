/*
 * MoneyMinder - Rest api
 * Api for MoneyMinder project
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.GetAllLists200Response;
import org.openapitools.client.model.ListRequestBody;
import org.openapitools.client.model.ModelList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ListsApi
 */
@Disabled
public class ListsApiTest {

    private final ListsApi api = new ListsApi();

    /**
     * Create new list
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createNewListTest() throws ApiException {
        ModelList response = api.createNewList();
        // TODO: test validations
    }

    /**
     * Delete specific list
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteSpecificListTest() throws ApiException {
        Integer listId = null;
        api.deleteSpecificList(listId);
        // TODO: test validations
    }

    /**
     * Get all lists
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAllListsTest() throws ApiException {
        GetAllLists200Response response = api.getAllLists();
        // TODO: test validations
    }

    /**
     * Return specific list
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSpecificListTest() throws ApiException {
        Integer listId = null;
        ModelList response = api.getSpecificList(listId);
        // TODO: test validations
    }

    /**
     * Update specific list
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateSpecificListTest() throws ApiException {
        Integer listId = null;
        ListRequestBody listRequestBody = null;
        ModelList response = api.updateSpecificList(listId, listRequestBody);
        // TODO: test validations
    }

}
