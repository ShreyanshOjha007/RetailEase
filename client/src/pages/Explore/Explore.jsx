import './Explore.css';
import {useContext, useState} from "react";
import {AppContext} from "../../context/AppContext.jsx";
import DisplayCategories from "../../components/DisplayCategories/DisplayCategories.jsx";
import DisplayItems from "../../components/DisplayItems/DisplayItems.jsx";
import CustomerForm from "../../components/CustomerForm/CustomerForm.jsx";
import CartItems from "../../components/CartItems/CartItems.jsx";
import CartSummary from "../../components/CartSummary/CartSummary.jsx";

const Explore = () => {

    const [selectedCategories, setSelectedCategories] = useState('')
    const [customerName, setCustomerName] = useState('');
    const [mobileNumber, setMobileNumber] = useState('');
    const {categories} = useContext(AppContext);

    return (
        <div className="explore-container text-light">
            <div className="left-column">
                <div className="first-row" style={{overflowY: "auto"}}>
                    <DisplayCategories categories={categories} selectedCategories={selectedCategories} setSelectedCategories={setSelectedCategories}/>
                </div>
                <hr className="horizontal-line" />
                <div className="second-row" style={{overflowY: "auto"}}>
                    <DisplayItems selectedCategories={selectedCategories}/>
                </div>
            </div>
            <div className="right-column d-flex flex-column">
                <div className="customer-form-container" style={{height: '20%'}}>
                    <CustomerForm customerName={customerName}
                                  setCustomerName={setCustomerName}
                                  mobileNumber={mobileNumber}
                                  setMobileNumber={setMobileNumber} />
                </div>
                <hr className="my-3 text-light"/>
                <div className="cart-item-container" style={{height: '50%'}}>
                    <CartItems customerName={customerName}
                               setCustomerName={setCustomerName}
                               mobileNumber={mobileNumber}
                               setMobileNumber={setMobileNumber}/>
                </div>
                <hr className="my-3 text-light"/>
                <div className="cart-summary-container" style={{height: '30%'}}>
                    <CartSummary customerName={customerName}
                                 setCustomerName={setCustomerName}
                                 mobileNumber={mobileNumber}
                                 setMobileNumber={setMobileNumber}
                    />
                </div>
            </div>

        </div>
    )
}

export default Explore;