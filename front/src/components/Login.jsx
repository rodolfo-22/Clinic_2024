import React, { useState } from 'react';
import authService from '../services/authService';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [identifier, setIdentifier] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const { token, role } = await authService.login(identifier, password);
            if (token) {
                switch (role) {
                    case 'Administrador':
                        navigate('/admin');
                        break;
                    case 'Asistente':
                        navigate('/asistente');
                        break;
                    case 'Doctor':
                        navigate('/doctor');
                        break;
                    default:
                        navigate('/paciente');
                        break;
                }
            }
        } catch (err) {
            setError('Credenciales incorrectas.');
        }
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center">Inicio de Sesión</h2>
                <form onSubmit={handleLogin} className="space-y-6">
                    <div>
                        <label className="block text-gray-700">Identificador</label>
                        <input
                            type="text"
                            value={identifier}
                            onChange={(e) => setIdentifier(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            placeholder="Ingrese su identificador"
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
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg"
                    >
                        Ingresar
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;
