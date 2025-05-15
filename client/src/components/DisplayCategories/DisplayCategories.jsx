import "./DisplayCategories.css"
import Category from "../Category/Category.jsx";

export default function DisplayCategories({categories,selectedCategories,setSelectedCategories}) {
    return (
        <div className="row g-3" style={{width: '100%' , margin: 0}}>
            {categories.map(category => (
                <div className="col-md-3 col-sm-6" key={category.categoryId} style={{padding: '0 10px'}}>
                    <Category
                        categoryName={category.name}
                        imgUrl={category.imgUrl}
                        bgColor={category.bgColor}
                        numberOfItems={category.items}
                        isSelected={selectedCategories === category.categoryId}
                        onClick={() => {
                            if(selectedCategories === category.categoryId) {
                                setSelectedCategories(null);
                            }
                            else {
                                setSelectedCategories(category.categoryId);
                            }
                        }}
                    />
                </div>
                ))}
        </div>
    )
}