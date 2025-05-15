import axios from 'axios';

export const fetchDashboardData = async () => {
    return await axios.get(`${import.meta.env.VITE_BASE_URL}/dashboard`, {headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}});
}