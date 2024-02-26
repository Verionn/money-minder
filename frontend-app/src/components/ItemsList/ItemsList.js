import { useParams } from "react-router-dom";
import { NavigateToSelectedList } from "./ItemListHelper";
import { Styles } from "./styles";
import { useContextElements } from "../../utils/hooks/customHooks";
import ItemBox from "./itemBox";
import AddNewItem from "./addNewItem";
const ItemsList = () => {
  const { listId } = useParams();

  const { windowWidth, isDarkMode } = useContextElements();
  const styles = Styles({ darkMode: isDarkMode, windowWidth });
  return (
    <div className="ItemContainer" style={{ ...styles.ItemsContainer }}>
      <NavigateToSelectedList listId={listId} />
      <div style={{ }}>
        <ItemBox listId={listId} />
        <AddNewItem listId={listId} />
      </div>
    </div>
  );
};

export default ItemsList;
