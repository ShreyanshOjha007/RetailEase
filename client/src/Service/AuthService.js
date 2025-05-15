import axios from 'axios';

export const login = async (data) => {
    try {
        const response = await axios.post(`${import.meta.env.VITE_BASE_URL}/login`, data);
        return response.data;
    } catch (error) {
        console.error("Login error:", error);
        throw error; // propagate to caller (optional)
    }
};