import axios from 'axios';

export const addItem = async (item) => {
    try {
        const response = await axios.post(`${import.meta.env.VITE_BASE_URL}/admin/item/add`,item,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
        return response.data;
    }catch (error) {
        console.error("Error while adding the item :", error);
        throw error;
    }
}

export const deleteItem = async (itemId) => {
    try{
        const response = await axios.delete(`${import.meta.env.VITE_BASE_URL}/admin/item/delete/${itemId}`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
        return response.data;
    }catch (error) {
        console.error("Error while deleting the item :", error);
        throw error;
    }
}

export const fetchItems = async () => {
    try{
        const response = await axios.get(`${import.meta.env.VITE_BASE_URL}/item/readAll`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
        return response.data;
    }catch (error) {
        console.error("Error while fetching the items :", error);
        throw error;
    }
}