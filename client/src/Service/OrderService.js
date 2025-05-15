import axios from 'axios';

export const fetchRecentOrders = async () => {
    return await axios.get(`${import.meta.env.VITE_BASE_URL}/orders/recent`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const deleteOrder = async (orderId) => {
    return await axios.delete(`${import.meta.env.VITE_BASE_URL}/orders/delete/${orderId}`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const createOrder = async (order) => {
    return await axios.post(`${import.meta.env.VITE_BASE_URL}/orders/place`, order, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}
