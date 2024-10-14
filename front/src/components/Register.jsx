import React, { useState } from 'react';
import authService from '../services/authService';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await authService.register(username, email, password);
            navigate('/login');
        } catch (err) {
            setError('Error al registrar el usuario.');
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center">Registro</h2>
                <form onSubmit={handleRegister} className="space-y-6">
                    <div>
                        <label className="block text-gray-700">Nombre de Usuario</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            placeholder="Ingrese su nombre de usuario"
                        />
                    </div>
                    <div>
                        <label className="block text-gray-700">Correo Electrónico</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            placeholder="Ingrese su correo electrónico"
                        />
                    </div>
                    <div>
                        <label className="block text-gray-700">Contraseña</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            placeholder="Ingrese su contraseña"
                        />
                    </div>
                    {error && <p className="text-red-500">{error}</p>}
                    <button
                        type="submit"
                        className="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-lg"
                    >
                        Registrar
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Register;
