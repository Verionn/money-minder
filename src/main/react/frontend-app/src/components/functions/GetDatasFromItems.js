// GetNumberOfItems.js
import React from "react";
import {
  GetItemListData,
} from "../communicationWithServer/HandleDataRequest";

const GetDatasFromItems = ({ listID, operation }) => {
  const apiUrl = `http://localhost:8080/lists/${listID}/items`;

  let { items, loading, error } = GetItemListData({ apiUrl });
  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }
  if (operation === "count") return <div>{items.length}</div>;
  else if (operation === "price")
    return <div>{calculateTotalPrice(items)}</div>;
};

export default GetDatasFromItems;

const calculateTotalPrice = (items) => {
  return items
    .reduce((total, item) => total + item.price * item.amount, 0)
    .toFixed(2);
};

export const GetCategoryNameID = async (categoryName, categories) => {
  let categoryID = 0;
  let found = false;

  categories.forEach((category) => {
    if (category.name === categoryName) {
      categoryID = category.categoryId;
      found = true;
    }
  });
  if (found) return categoryID;
  return -1;
};
