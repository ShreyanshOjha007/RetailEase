import axios from "axios";

export const addUser = async (user) => {
    return await axios.post(`${import.meta.env.VITE_BASE_URL}/admin/register`, user, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const deleteUser = async (userId) => {
    return await axios.delete(`${import.meta.env.VITE_BASE_URL}/admin/delete/${userId}`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const fetchUsers = async () => {
    return await axios.get(`${import.meta.env.VITE_BASE_URL}/admin/users`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}