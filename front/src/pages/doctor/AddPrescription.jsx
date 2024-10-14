import React, { useState } from 'react';
import prescriptionService from '../../services/prescriptionService';

const AddPrescriptionPage = () => {
    const [appointmentId, setAppointmentId] = useState('');
    const [prescriptions, setPrescriptions] = useState([{ medicine: '', dose: '', endDate: '' }]);
    const [successMessage, setSuccessMessage] = useState('');

    const handlePrescriptionChange = (index, field, value) => {
        const newPrescriptions = [...prescriptions];
        newPrescriptions[index][field] = value;
        setPrescriptions(newPrescriptions);
    };

    const handleAddPrescription = () => {
        setPrescriptions([...prescriptions, { medicine: '', dose: '', endDate: '' }]);
    };

    const handleSubmit = async () => {
        try {
            await prescriptionService.addPrescriptions(appointmentId, { prescriptions });
            setSuccessMessage('Prescriptions added successfully');
            setAppointmentId('');
            setPrescriptions([{ medicine: '', dose: '', endDate: '' }]);
        } catch (error) {
            console.error('Error adding prescriptions:', error);
            setSuccessMessage('Error adding prescriptions');
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
            <h1 className="text-3xl font-bold text-black mb-5">Añadir Prescripciones</h1>
            <div className="w-full max-w-4xl mt-5">
                <label className="block mb-2 font-medium text-gray-700">
                    ID de la Cita Médica:
                    <input 
                        type="text" 
                        value={appointmentId} 
                        onChange={(e) => setAppointmentId(e.target.value)} 
                        className="mt-1 block w-full p-2 border border-gray-300 rounded"
                    />
                </label>
                {prescriptions.map((prescription, index) => (
                    <div key={index} className="mb-5 p-4 bg-white rounded-lg shadow border border-gray-300">
                        <label className="block mb-2 font-medium text-gray-700">
                            Medicamento:
                            <input 
                                type="text" 
                                value={prescription.medicine} 
                                onChange={(e) => handlePrescriptionChange(index, 'medicine', e.target.value)} 
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>
                        <label className="block mb-2 font-medium text-gray-700">
                            Dosis:
                            <input 
                                type="text" 
                                value={prescription.dose} 
                                onChange={(e) => handlePrescriptionChange(index, 'dose', e.target.value)} 
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>
                        <label className="block mb-2 font-medium text-gray-700">
                            Fecha de Finalización:
                            <input 
                                type="date" 
                                value={prescription.endDate} 
                                onChange={(e) => handlePrescriptionChange(index, 'endDate', e.target.value)} 
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>
                    </div>
                ))}
                <button 
                    onClick={handleAddPrescription} 
                    className="mb-5 p-2 bg-blue-500 text-white rounded">
                    Añadir Otra Prescripción
                </button>
                <button 
                    onClick={handleSubmit} 
                    className="mb-5 p-2 bg-green-500 text-white rounded">
                    Guardar Prescripciones
                </button>
                {successMessage && (
                    <div className="mt-5 p-4 bg-green-100 text-green-700 border border-green-500 rounded">
                        {successMessage}
                    </div>
                )}
            </div>
        </div>
    );
};

export default AddPrescriptionPage;
