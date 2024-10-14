import React, { useState, useEffect } from 'react';
import historyService from '../../services/historyService2';

const PatientMedicalHistoryPage = () => {
    const [medicalHistory, setMedicalHistory] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchMedicalHistory = async () => {
            try {
                const data = await historyService.getMyHistory(); // Llamada para obtener el historial del usuario autenticado
                setMedicalHistory(data);
            } catch (error) {
                setError('Error fetching medical history');
                console.error('Error fetching medical history:', error);
            }
        };

        fetchMedicalHistory();
    }, []);

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
            <div className="w-full max-w-2xl px-4"> {/* Ajuste aquí para centrar y limitar el ancho */}
                <h1 className="text-3xl font-bold text-black mb-5 text-center">Mi Historial Médico</h1>
                <div className="border border-gray-300 bg-white p-6 rounded-lg shadow-lg">
                    <h2 className="text-xl font-bold mb-4">Historial Médico</h2>
                    {error && <p className="text-red-500">{error}</p>}
                    <ul className="space-y-4">
                        {medicalHistory.length === 0 ? (
                            <p>No se encontró historial médico.</p>
                        ) : (
                            medicalHistory.map(history => (
                                <li key={history.id} className="p-4 bg-white rounded-lg shadow border border-gray-200">
                                    <p><strong>Fecha:</strong> {new Date(history.timestamp).toLocaleDateString()}</p>
                                    <p><strong>Razón:</strong> {history.reason}</p>
                                </li>
                            ))
                        )}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default PatientMedicalHistoryPage;
