import React, { useState } from 'react';
import prescriptionService from '../../services/prescriptionService';

const PatientPrescriptions = () => {
    const [userId, setUserId] = useState('');
    const [prescriptions, setPrescriptions] = useState([]);

    const handleSearch = async () => {
        try {
            const data = await prescriptionService.getPrescriptionsByUserId(userId);
            setPrescriptions(data);
        } catch (error) {
            console.error('Error fetching prescriptions:', error);
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
        <div className="w-full max-w-2xl px-4"> {/* Ajuste aquí para centrar y limitar el ancho */}
            <h1 className="text-3xl font-bold text-black mb-5 text-center">Vista del Doctor</h1>
            <div className="bg-white p-6 rounded-lg shadow-lg mb-5"> {/* Caja con borde redondeado */}
                <div className="mb-5">
                    <label className="block mb-4 font-medium text-gray-700">
                        ID del Usuario:
                        <input 
                            type="text" 
                            value={userId} 
                            onChange={(e) => setUserId(e.target.value)} 
                            className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
                        />
                    </label>
                </div>
                <div className="flex justify-center">
                    <button 
                        onClick={handleSearch} 
                        className="p-2 bg-blue-500 text-white rounded-md">
                        Buscar Prescripciones
                    </button>
                </div>
            </div>
            <div className="border border-gray-300 bg-white p-6 rounded-lg shadow-lg">
                <h2 className="text-xl font-bold mb-4">Prescripciones Vigentes</h2>
                <ul className="space-y-4">
                    {prescriptions.length === 0 ? (
                        <p>No se encontraron prescripciones vigentes para el usuario.</p>
                    ) : (
                        prescriptions.map(prescription => (
                            <li key={prescription.id} className="p-4 bg-white rounded-lg shadow border border-gray-200">
                                <p><strong>Medicamento:</strong> {prescription.medicine}</p>
                                <p><strong>Dosis:</strong> {prescription.dose}</p>
                                <p><strong>Fecha de Finalización:</strong> {new Date(prescription.endDate).toLocaleDateString()}</p>
                            </li>
                        ))
                    )}
                </ul>
            </div>
        </div>
    </div>
    );
};

export default PatientPrescriptions;
