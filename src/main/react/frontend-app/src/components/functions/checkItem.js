export const checkItem = async (item) => {
    try {
      
      console.log(item.listId, item.itemId);
      const response = await fetch(
        `http://localhost:8080/lists/${item.listId}/items/${item.itemId}/bought`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            // Add any other headers if required (e.g., authentication headers)
          },
          body: JSON.stringify(item)
        }
      );
  
      if (!response.ok) {
        const responseBody = await response.json();
        console.error("Failed to delete item. Server response:", responseBody);
        throw new Error("Failed to delete item");
      }
  
      // Check if the response body is not empty before attempting to parse
      const responseBody = await response.text();
      if (responseBody.trim()) {
        console.log("Item checked successfully");
      } else {
        console.log("Item checked successfully (empty response body)");
      }
    } catch (error) {
      console.error("Error checking item:", error);
    }
  };
  