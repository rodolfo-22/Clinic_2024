import React, { useState, useEffect } from 'react';
import prescriptionService from '../../services/prescriptionService';

const UserPrescriptions = () => {
    const [prescriptions, setPrescriptions] = useState([]);

    useEffect(() => {
        fetchPrescriptions();
    }, []);

    const fetchPrescriptions = async () => {
        try {
            const data = await prescriptionService.getUserPrescriptions();
            setPrescriptions(data);
        } catch (error) {
            console.error('Error fetching prescriptions:', error);
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
        <div className="w-full max-w-2xl px-4"> {/* Ajuste aquí para centrar y limitar el ancho */}
            <h1 className="text-3xl font-bold text-black mb-5 text-center">Mis Prescripciones</h1>
            <div className="bg-white p-6 rounded-lg shadow-lg"> {/* Caja con borde redondeado */}
                <h2 className="text-xl font-bold mb-4">Prescripciones Vigentes</h2>
                <ul className="space-y-4">
                    {prescriptions.length === 0 ? (
                        <p>No tienes prescripciones vigentes.</p>
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

export default UserPrescriptions;
