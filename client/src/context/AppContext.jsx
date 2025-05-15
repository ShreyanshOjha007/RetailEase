import {createContext, useEffect, useState} from "react";
import {fetchCategory} from "../service/CategoryService.js";
import {fetchItems} from "../service/ItemService.js";

export const AppContext = createContext(null);

export const AppContextProvider = (props) => {

    const [items, setItems] = useState([]);

    const [categories, setCategories] = useState([]);

    const [auth, setAuth] = useState({token: null, role: null});

    const [cartItems, setCartItems] = useState([]);

    const addItemToCart = (item) => {
        const existingItem = cartItems.find(cartItem => cartItem.name === item.name);
        if(existingItem) {
            setCartItems(cartItems.map(cartItem => cartItem.name === item.name ? {...cartItem,quantity: cartItem.quantity + 1} : cartItem));
        }else{
            setCartItems([...cartItems, {...item, quantity: 1}]);
        }
    }

    const removeItemFromCart = (itemId) => {
        setCartItems(cartItems.filter(cartItem => cartItem.itemId !== itemId));
    }

    const updateItemQuantity = (itemId, newQuantity) => {
        setCartItems(cartItems.map(cartItem => cartItem.itemId === itemId ? {...cartItem,quantity: newQuantity} : cartItem));
    }

    useEffect(() => {
        async function loadData() {
            if (localStorage.getItem("token") && localStorage.getItem("role")) {
                setAuthData(
                    localStorage.getItem("token"),
                    localStorage.getItem("role")
                );
            }
            const categoryResponse = await fetchCategory();
            const itemResponse = await fetchItems()
            setCategories(categoryResponse.data);
            setItems(itemResponse);
        }
        loadData();
    }, []);

    const setAuthData = (token, role) => {
        setAuth({token, role});
    }

    const clearCart = () => {
        setCartItems([]);
    }

    const contextValue = {
        categories,
        setCategories,
        auth,
        setAuthData,
        items,
        setItems,
        addItemToCart,
        cartItems,
        removeItemFromCart,
        updateItemQuantity,
        clearCart,
    }

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
}
