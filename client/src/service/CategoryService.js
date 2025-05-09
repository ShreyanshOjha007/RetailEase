import axios from "axios";

export const addCategory = async (category) => {
    return await axios.post('http://localhost:8085/api/v1.0/admin/categories/add',category,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const deleteCategory = async (categoryId) => {
    return await axios.delete(`http://localhost:8085/api/v1.0/admin/categories/delete/${categoryId}`,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const fetchCategory = async () => {
    return await axios.get('http://localhost:8085/api/v1.0/categories/getAll',{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}