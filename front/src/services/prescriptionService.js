import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/clinic';

const addPrescriptions = async (appointmentId, prescriptionData) => {
    const token = getToken();
    const response = await axios.post(`${API_URL}/prescription/${appointmentId}`, prescriptionData, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

const getPrescriptionsByUserId = async (userId) => {
    const token = getToken();
    if (!token) {
        throw new Error('No token found');
    }

    try {
        const response = await axios.get(`${API_URL}/prescription/${userId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data.data; // Asegúrate de que 'data' contiene la lista de prescripciones
    } catch (error) {
        console.error('Error fetching prescriptions:', error);
        throw error;
    }
};

const getUserPrescriptions = async () => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/prescriptions`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data.data; // Asegúrate de que 'data' contiene la lista de prescripciones
};

export default {
    getPrescriptionsByUserId,
    getUserPrescriptions,
    addPrescriptions,
};
