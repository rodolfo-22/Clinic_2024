import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/patient';

const getMyHistory = async () => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/record/list`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data.data;
};

export default {
    getMyHistory,
};
