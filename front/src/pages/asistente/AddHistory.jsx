import React, { useState } from 'react';
import axios from 'axios';
import { getToken } from '../../services/authService';

const AddHistory = () => {
    const [identifier, setIdentifier] = useState('');
    const [reason, setReason] = useState('');
    const [message, setMessage] = useState('');

    const handleAddHistory = async () => {
        const token = getToken();
        try {
            const response = await axios.post('http://localhost:8080/api/users/record', {
                identifier,
                reason
            }, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setMessage('History added successfully');
        } catch (error) {
            setMessage('Failed to add history');
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
            <h1 className="text-3xl font-bold text-black mb-5">Añadir historial a un usuario</h1>
            <div className="w-full max-w-md">
                <label className="block mb-2 font-medium text-gray-700">
                    Identificador de usuario (Username or Email):
                    <input
                        type="text"
                        value={identifier}
                        onChange={(e) => setIdentifier(e.target.value)}
                        className="mt-1 block w-full p-2 border border-gray-300 rounded"
                    />
                </label>
                <label className="block mb-4 font-medium text-gray-700">
                    Razon:
                    <textarea
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        className="mt-1 block w-full p-2 border border-gray-300 rounded"
                    />
                </label>
                <button
                    onClick={handleAddHistory}
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                    Añadir historial
                </button>
                {message && <p className="mt-4 text-red-500">{message}</p>}
            </div>
        </div>
    );
};

export default AddHistory;
