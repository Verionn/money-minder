import React, { useState } from "react";

import GetLists from "../../../components/communicationWithBackEnd/GetLists";
import ListDescription from "../../../components/listDescription/listDescriptions";
import "./DisplayAllLists.css";
import GetDatasFromItems from "../../../components/functions/GetDatasFromItems";
import DisplayItems from "../DisplayItems/DisplayItems";
import ListDropdown from "../../../components/dropdownMenuLists/DropdownMenuList";

const DisplayAllLists = () => {
  const [ItemsID, setItemsID] = useState(-1);
  console.log("ItemsID is : " + ItemsID);

  const handleListClick = (listId) => {
    // Do something with the list ID, such as logging or triggering another action
    console.log(`List clicked with ID: ${listId}`);
    setItemsID(listId);
  };

  const handleCloseItemsList = () => {
    console.log("closeItemsList");
    setItemsID(-1);
  };

  const apiUrl = "http://localhost:8080/lists";
  let { data, loading, error } = GetLists({ apiUrl });
  const getListName = (listID) => {
    if (data && data.lists && data.lists.length > 0) {
      return data.lists.find((list) => list.listId === listID).name;
    }
    return "List";
  };
  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
    <div className={ItemsID == -1 ? "listBox" : "listBoxSelectItems"}>
      {ItemsID === -1 && data && data.lists && data.lists.length > 0 ? null : (
        <div className="listDropdown">   <ListDropdown
        
        lists={data.lists}
        onSelect={handleListClick}
      /></div>
     
      )}

      {ItemsID === -1 ? (
        data && data.lists && data.lists.length > 0 ? (
          <ul className="userLists">
            {data.lists.map((list) => (
              <li
                key={list.listId}
                className="singleList"
                onClick={() => handleListClick(list.listId)}
              >
                <div className="listTitle">{list.name}</div>
                <div className={"listHeader"}>
                  <span className="NumberOfItems">
                    Number of Items :{" "}
                    <GetDatasFromItems
                      listID={list.listId}
                      operation={"count"}
                    />
                  </span>
                  <span className="FullPrice">
                    Total Price :{" "}
                    <GetDatasFromItems
                      listID={list.listId}
                      operation={"price"}
                    />{" "}
                    $
                  </span>
                </div>
                <div className="Description">
                  <ListDescription
                    Description={
                      list.description === ""
                        ? "No description"
                        : list.description
                    }
                    onClick={() => console.log("Clicked on description")}
                  ></ListDescription>
                </div>
              </li>
            ))}
          </ul>
        ) : (
          <p>No data available.</p>
        )
      ) : null}

      {ItemsID !== -1 && (
        <div>
          {/* Content to render when ItemsID is not -1 */}
          <DisplayItems
            listID={ItemsID}
            operation={getListName(ItemsID)}
            onClose={handleCloseItemsList}
          />
        </div>
      )}
    </div>
  );
};

export default DisplayAllLists;
