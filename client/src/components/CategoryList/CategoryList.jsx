import {useContext, useState} from "react";
import {AppContext} from "../../context/AppContext.jsx";
import "./CategoryList.css"
import {deleteCategory} from "../../Service/CategoryService.js";
import toast from "react-hot-toast";

const CategoryList = () => {
    const {categories,setCategories} = useContext(AppContext);

    const [searchTerm, setSearchTerm] = useState('');

    const filteredCategories = categories.filter(category =>
        category.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const deleteByCategoryId = async (categoryId) => {
        try{
            const response = await deleteCategory(categoryId);
            if(response.status === 204) {
                const updatedCategories = categories.filter(category => category.categoryId !== categoryId);
                setCategories(updatedCategories);
                toast.success("Category deleted successfully");
            }else{
                toast.error("Unable to delete category");
            }
        }catch (error) {
            console.log(error);
            toast.error("Error while deleting category");
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
                {filteredCategories.map((category, index) => (
                    <div className="col-12" key={index}>
                        <div className="card p-3" style={{backgroundColor: category.bgColor}}>
                            <div className="d-flex align-items-center">
                                <div style={{marginRight: '15px'}}>
                                    <img src={category.imgUrl} alt={category.name} className="category-image"/>
                                </div>
                                <div className="flex-grow-1">
                                    <h5 className="mb-1 text-black">{category.name}</h5>
                                    <p className="mb-0 text-black">{category.items} Items</p>
                                </div>
                                <div>
                                    <button className="btn btn-danger btn-sm"
                                    onClick={() => deleteByCategoryId(category.categoryId)}>
                                        <i className="bi bi-trash"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default CategoryList;
