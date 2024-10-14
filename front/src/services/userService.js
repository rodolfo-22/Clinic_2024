import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/users/';

const getUsers = async () => {
    const token = getToken(); // Obtén el token desde el localStorage
    const response = await axios.get(API_URL, {
        headers: {
            'Authorization': `Bearer ${token}` // Agrega el prefijo Bearer
        }
    });
    return response.data.data; // Asegúrate de que 'data' contiene la lista de usuarios
};

export default {
    getUsers,
};
