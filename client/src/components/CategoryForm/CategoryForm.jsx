import {useContext, useEffect, useState} from "react";
import {assets} from "../../Assets/assets.js";
import toast from "react-hot-toast";
import {addCategory} from "../../Service/CategoryService.js";
import {AppContext} from "../../context/AppContext.jsx";


const CategoryForm = () => {
    const {categories,setCategories} = useContext(AppContext);
    const [loading, setLoading] = useState(false);
    const [image, setImage] = useState('');
    const [data, setData] = useState({
        name: "",
        description : "",
        bgColor : '#010101'
    })

    useEffect(() => {
        console.log(data);
    },[data])


    const onChangeHandler =  (e) => {
        const value = e.target.value;
        const name = e.target.name;
        setData({
            ...data,
            [name]: value
        })
    }

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        setLoading(true);
        if(!image) {
            toast.error("Please select an image");
            setLoading(false);
            return;
        }
        const formData = new FormData();
        formData.append('category', JSON.stringify(data));
        formData.append('file',image);
        for (let [key, value] of formData.entries()) {
            console.log(key, value);
        }
        try{
            const response = await addCategory(formData);
            if(response.status === 201) {
                setCategories([...categories, response.data]);
                toast.success("Category added successfully");
                setData(
                    {
                        name: "",
                        description : "",
                        bgColor : '#010101'
                    }
                )
                setImage('');
            }
        }catch (error) {
            console.log(error);
            toast.error("Error while adding category");
        }finally {
            setLoading(false);
        }

    }

    return (
        <div className="mx-2 mt-2 object-fit-contain">
            <div className="row">
                <div className="card col-md-12 form-container background-color-white">
                    <div className="card-body">
                        <form onSubmit={onSubmitHandler}>
                            <div className="mb-2">
                                <label htmlFor="image" className="form-label">
                                    <img src={image ? URL.createObjectURL(image) : assets.upload} alt="Upload" width={48} />
                                </label>
                                <input type="file" id="image" className="form-control" hidden onChange={event => setImage(event.target.files[0])}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="name" className="form-label">Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="name"
                                    name="name"
                                    placeholder="Category Name"
                                    value={data.name}
                                    onChange={onChangeHandler}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Description</label>
                                <textarea
                                    rows="3"
                                    className="form-control"
                                    id="description"
                                    name="description"
                                    placeholder="Write content here..."
                                    onChange={onChangeHandler}
                                    value={data.description}
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="bgColor" className="form-label">Background Color</label>
                                <br />
                                <input
                                    type="color"
                                    name="bgColor"
                                    id="bgColor"
                                    className="form-control form-control-color"
                                    placeholder="#ffffff"
                                    onChange={onChangeHandler}
                                    value={data.bgColor}
                                />
                            </div>
                            <button type="submit"
                                    disabled={loading}
                                    className="btn btn-warning w-100">{loading ? "Loading..." : "Submit"}</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CategoryForm;