import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

const login = async (identifier, password) => {
    const response = await axios.post(`${API_URL}/login`, { identifier, password });
    const { token, role } = response.data.data; // Accediendo a token y rol dentro de 'data'
    if (token && role) {
        localStorage.setItem('token', token);
        localStorage.setItem('role', role); // Almacenar el rol en localStorage
    }
    return { token, role };
};

const register = async (username, email, password) => {
    const response = await axios.post(`${API_URL}/register`, { username, email, password });
    return response.data;
};

export default {
    login,
    register,
};

export const getToken = () => {
    const token = localStorage.getItem('token');
    return token;
};
