import {useContext, useState} from "react";
import {deleteItem} from "../../Service/ItemService.js";
import toast from "react-hot-toast";
import {AppContext} from "../../context/AppContext.jsx";
import "./ItemList.css";

const ItemList = () => {
    const {items,setItems,setCategories} = useContext(AppContext);
    const [searchTerm, setSearchTerm] = useState('');

    const filteredItems = items.filter(
        item => item.name.toLowerCase().includes(searchTerm.toLowerCase())
    )

    async function deleteByItemId(itemId) {
        try {
            await deleteItem(itemId);
            const updatedItems = items.filter(item => item.itemId !== itemId);
            const deletedItem = items.filter(item => item.itemId === itemId);
            setCategories((prevCategories) =>
                prevCategories.map((category) => category.categoryId === deletedItem[0].categoryId ? {...category, items: category.items - 1} : category));
            setItems(updatedItems);
            toast.success("Item deleted successfully");
        }catch (error) {
            console.log(error);
            toast.error("Error while deleting item");
        }

    }
    return (
        <div className="category-list-container" style={{height: '100vh' , overflowY: 'auto' , overflowX: 'hidden'}}>
            <div className="row pe-2">
                <div className="input-group pe-2 mb-3">
                    <input
                        type="text"
                        name="keyword"
                        id="keyword"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search by keyword"
                        className="form-control"
                    />
                    <span className="input-group-text bg-warning border-warning">
                        <i className="bi bi-search"></i>
                    </span>
                </div>
            </div>
            <div className="row g-3 pe-2">
                {filteredItems.map((item, index) => (
                    <div className="col-12" key={index}>
                        <div className="card p-3" style={{backgroundColor: item.bgColor}}>
                            <div className="d-flex align-items-center">
                                <div style={{marginRight: '15px'}}>
                                    <img src={item.imgUrl} alt={item.name} className="item-image"/>
                                </div>
                                <div className="flex-grow-1">
                                    <h5 className="mb-1 text-black">{item.name}</h5>
                                    <p className="mb-0 text-black">
                                        Category: {item.categoryName}</p>
                                    <span className="mb-0 text-block badge rounded-pill text-bg-warning">
                                        <i className="bi bi-currency-rupee"></i>
                                        Price: {item.price}
                                    </span>
                                </div>
                                <div>
                                    <button className="btn btn-danger btn-sm"
                                            onClick={() => deleteByItemId(item.itemId)}>
                                        <i className="bi bi-trash"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ItemList;