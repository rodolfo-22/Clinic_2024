import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api';

const approveAppointment = async (appointmentData) => {
    const token = getToken(); // Obtener el token desde el localStorage
    const response = await axios.post(`${API_URL}/appointment/approve`, appointmentData, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

const createAppointment = async (appointment) => {
    const token = getToken();
    const response = await axios.post(`${API_URL}/appointment/request`, appointment, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

const getAppointments = async (state) => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/appointment/own`, {
        headers: {
            'Authorization': `Bearer ${token}`
        },
        params: { state }
    });
    return response.data;
};

const cancelAppointment = async (id) => {
    const token = getToken();
    const response = await axios.delete(`${API_URL}/appointment/cancel/${id}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

const getAllAppointments = async () => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/appointment/list`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data.data; // Asegúrate de que 'data' contiene la lista de citas
};

const getAvailableDoctors = async (startTime, duration) => {
    const token = getToken();
    const response = await axios.get(`${API_URL}/appointment/available-doctors`, {
        headers: {
            'Authorization': `Bearer ${token}`
        },
        params: { startTime, duration }
    });
    return response.data.data;
};

const getSpecialities = async () => {
    const token = getToken();
    try {
        const response = await axios.get(`${API_URL}/specialities`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data; // Verifica que estás devolviendo la respuesta correcta
    } catch (error) {
        console.error('Error fetching specialities:', error.response);
        throw error;
    }
};

const startAppointment = async (appointmentId) => {
    const token = getToken();
    const response = await axios.post(`${API_URL}/appointment/start/${appointmentId}`, null, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

const finishAppointment = async (appointmentId) => {
    const token = getToken();
    const response = await axios.post(`${API_URL}/appointment/finish/${appointmentId}`, null, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return response.data;
};

export default {
    startAppointment,
    finishAppointment,
    approveAppointment,
    createAppointment,
    getAppointments,
    cancelAppointment,
    getAllAppointments,
    getAvailableDoctors, 
    getSpecialities,
};
