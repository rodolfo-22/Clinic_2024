import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/clinic';

const getSchedule = async (date) => {
    const token = getToken();
    console.log('Requesting schedule for date:', date); // Log para depuración
    try {
        const response = await axios.get(`${API_URL}/schedule`, {
            headers: {
                'Authorization': `Bearer ${token}`
            },
            params: { date }
        });
        console.log('API response:', response.data); // Log para depuración
        return response.data.data;
    } catch (error) {
        console.error('Error fetching schedule:', error); // Log de error
        throw error;
    }
};

export default {
    getSchedule
};
