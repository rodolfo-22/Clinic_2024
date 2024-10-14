import React, { useState } from 'react';
import scheduleService from '../../services/scheduleService';

const DoctorSchedulePage = () => {
    const [date, setDate] = useState('');
    const [appointments, setAppointments] = useState([]);

    const handleSearch = async () => {
        try {
            const data = await scheduleService.getSchedule(date);
            setAppointments(data);
        } catch (error) {
            console.error('Error fetching schedule:', error);
        }
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
        <div className="w-full max-w-2xl px-4"> {/* Ajuste aquí para centrar y limitar el ancho */}
            <h1 className="text-3xl font-bold text-black mb-5 text-center">Citas medicas por atender</h1>
            <div className="bg-white p-6 rounded-lg shadow-lg"> {/* Caja con borde redondeado */}
                <label className="block mb-4 font-medium text-gray-700">
                    Fecha:
                    <input 
                        type="date" 
                        value={date} 
                        onChange={(e) => setDate(e.target.value)} 
                        className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
                    />
                </label>
                <div className="flex justify-center mb-5">
                    <button 
                        onClick={handleSearch} 
                        className="p-2 bg-blue-500 text-white rounded-md">
                        Buscar 
                    </button>
                </div>
                <div className="border border-gray-300 bg-white p-6 rounded-lg shadow">
                    <h2 className="text-xl font-bold mb-4">Citas</h2>
                    <ul className="space-y-4">
                        {appointments.length === 0 ? (
                            <p>No se encontraron citas programadas para el dia seleccionado.</p>
                        ) : (
                            appointments.map(appointment => (
                                <li key={appointment.id} className="p-4 bg-white rounded-lg shadow border border-gray-200">
                                    <p><strong>Paciente:</strong> {appointment.userName || 'No disponible'}</p>
                                    <p><strong>Hora de Realización:</strong> {new Date(appointment.realizationDate).toLocaleTimeString()}</p>
                                    <p><strong>Razón:</strong> {appointment.reason}</p>
                                    {/* Agrega más detalles según sea necesario */}
                                </li>
                            ))
                        )}
                    </ul>
                </div>
            </div>
        </div>
    </div>
);
};

export default DoctorSchedulePage;
