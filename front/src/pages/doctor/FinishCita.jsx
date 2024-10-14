import React, { useState } from 'react';
import appointmentService from '../../services/appointmentService';

const ManageAppointmentPage = () => {
    const [appointmentId, setAppointmentId] = useState('');
    const [message, setMessage] = useState('');

    const handleStart = async () => {
        try {
            await appointmentService.startAppointment(appointmentId);
            setMessage('Cita iniciada con éxito');
        } catch (error) {
            console.error('Error iniciando la cita:', error);
            setMessage('Error iniciando la cita');
        }
    };

    const handleFinish = async () => {
        try {
            await appointmentService.finishAppointment(appointmentId);
            setMessage('Cita finalizada con éxito');
            setAppointmentId(''); // Limpiar el campo después de la operación
        } catch (error) {
            console.error('Error finalizando la cita:', error);
            setMessage('Error finalizando la cita');
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
        <div className="w-full max-w-2xl px-4"> {/* Ajuste aquí para centrar y limitar el ancho */}
            <h1 className="text-3xl font-bold text-black mb-5 text-center">Gestionar Cita Médica</h1>
            <div className="bg-white p-6 rounded-lg shadow-lg"> {/* Caja con borde redondeado */}
                <label className="block mb-4 font-medium text-gray-700">
                    ID de la Cita Médica:
                    <input 
                        type="text" 
                        value={appointmentId} 
                        onChange={(e) => setAppointmentId(e.target.value)} 
                        className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
                    />
                </label>
                <div className="flex justify-center space-x-4 mb-5"> {/* Espaciado entre botones */}
                    <button 
                        onClick={handleStart} 
                        className="p-2 bg-blue-500 text-white rounded-md">
                        Iniciar Cita
                    </button>
                    <button 
                        onClick={handleFinish} 
                        className="p-2 bg-green-500 text-white rounded-md">
                        Finalizar Cita
                    </button>
                </div>
                {message && <p className="mt-5 text-center text-green-600">{message}</p>}
            </div>
        </div>
    </div>
    );
};

export default ManageAppointmentPage;
