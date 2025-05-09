import axios from 'axios';

export const login = async (data) => {
    try {
        const response = await axios.post('http://localhost:8085/api/v1.0/login', data);
        return response.data;
    } catch (error) {
        console.error("Login error:", error);
        throw error; // propagate to caller (optional)
    }
};