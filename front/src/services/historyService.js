import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/users/record';

const addHistory = async (historyData) => {
    const token = getToken();
    const response = await axios.post(`${API_URL}/add`, historyData, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

export const getHistoryByUser = async (userId, startDate, endDate) => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/list`, {
        headers: {
            'Authorization': `Bearer ${token}`
        },
        params: { startDate, endDate }
    });
    return response.data.data; 
};

// Nueva función para obtener el historial del usuario autenticado
export const getMyHistory = async () => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/list`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data.data;
};

export default {
    addHistory,
    getHistoryByUser,
    getMyHistory,
};
