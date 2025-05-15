import axios from "axios";

export const addCategory = async (category) => {
    return await axios.post(`${import.meta.env.VITE_BASE_URL}/admin/categories/add`,category,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const deleteCategory = async (categoryId) => {
    return await axios.delete(`${import.meta.env.VITE_BASE_URL}/admin/categories/delete/${categoryId}`,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const fetchCategory = async () => {
    return await axios.get(`${import.meta.env.VITE_BASE_URL}/categories/getAll`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}