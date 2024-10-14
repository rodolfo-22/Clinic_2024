import React, { useState, useEffect } from 'react';
import appointmentService from '../../services/appointmentService';

const CrearCita = () => {
    const [reason, setReason] = useState('');
    const [date, setDate] = useState('');
    const [appointments, setAppointments] = useState([]);
    const [filterState, setFilterState] = useState('');

    useEffect(() => {
        fetchAppointments();
    }, [filterState]);

    const fetchAppointments = async () => {
        try {
            const response = await appointmentService.getAppointments(filterState);
            setAppointments(response.data || []);
        } catch (error) {
            console.error('Error fetching appointments:', error);
            setAppointments([]);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const appointment = { reason, date };
            await appointmentService.createAppointment(appointment);
            setReason('');
            setDate('');
            fetchAppointments();
        } catch (error) {
            console.error('Error creating appointment:', error);
        }
    };

    const handleCancelAppointment = async (appointmentId) => {
        if (!appointmentId) {
            console.error('Appointment ID is undefined');
            return;
        }
        try {
            await appointmentService.cancelAppointment(appointmentId);
            fetchAppointments();
        } catch (error) {
            console.error('Error cancelling appointment:', error);
        }
    };

    return (
        <div className="min-h-screen bg-blue-100 flex flex-col items-center py-10">
            <h1 className="text-3xl font-bold text-black mb-5">Realizar una Cita Médica</h1>
            <form onSubmit={handleSubmit} className="bg-white shadow-lg rounded-lg p-6 mb-10 w-full max-w-lg">
                <label className="block mb-4">
                    Razón de su cita:
                    <input
                        type="text"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        required
                        className="mt-2 p-2 border border-gray-300 rounded w-full"
                    />
                </label>
                <label className="block mb-4">
                    Día que desea ser atendido:
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                        className="mt-2 p-2 border border-gray-300 rounded w-full"
                    />
                </label>
                <div className="flex justify-between">
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                        Enviar Cita
                    </button>
                    <button
                        type="button"
                        onClick={() => { setReason(''); setDate(''); }}
                        className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                    >
                        Cancelar
                    </button>
                </div>
            </form>

            <h2 className="text-2xl font-bold mb-5">Citas Realizadas</h2>
            <label className="block mb-4">
                Filtrar por Estado:
                <select
                    value={filterState}
                    onChange={(e) => setFilterState(e.target.value)}
                    className="mt-2 p-2 border border-gray-300 rounded w-full"
                >
                    <option value="">Todos</option>
                    <option value="pending">Pendiente</option>
                    <option value="confirmed">Confirmada</option>
                    <option value="completed">Completada</option>
                    <option value="cancelled">Cancelada</option>
                </select>
            </label>

            <ul className="bg-white shadow-lg rounded-lg w-full max-w-lg p-6">
                {appointments.map((appointment) => (
                    <li key={appointment.id} className="mb-4">
                        <div className="flex justify-between items-center">
                            <div>
                                <span>{appointment.reason} - {appointment.startTimestamp ? new Date(appointment.startTimestamp).toLocaleString() : 'Fecha no definida'} ({appointment.state})</span>
                                <div>
                                    <span><strong>Usuario:</strong> {appointment.userName || 'N/A'} ({appointment.userEmail || 'N/A'})</span>
                                </div>
                            </div>
                            <button
                                onClick={() => handleCancelAppointment(appointment.id)}
                                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                            >
                                Cancelar
                            </button>
                        </div>
                        {appointment.prescriptions && appointment.prescriptions.length > 0 && (
                            <ul className="mt-2">
                                <h3 className="font-bold">Prescripciones:</h3>
                                {appointment.prescriptions.map((prescription, index) => (
                                    <li key={index} className="ml-4">
                                        {prescription}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CrearCita;
