import axios from 'axios';

export const createRazorpayOrder = async (data) => {
    return await axios.post(`${import.meta.env.VITE_BASE_URL}/payment/create-order`, data,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}

export const verifyRazorpayPayment = async (paymentData) => {
    return await axios.post(`${import.meta.env.VITE_BASE_URL}/orders/verifyPayment`, paymentData,{headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}