import React, { useState, useEffect } from 'react';
import appointmentService from '../../services/appointmentService';

const AppointmentApprovalPage = () => {
    const [requests, setRequests] = useState([]);
    const [selectedRequest, setSelectedRequest] = useState(null);
    const [specialty, setSpecialty] = useState('');
    const [duration, setDuration] = useState('');
    const [appointmentDate, setAppointmentDate] = useState('');
    const [appointmentTime, setAppointmentTime] = useState('');
    const [doctors, setDoctors] = useState([]);
    const [assignedDoctors, setAssignedDoctors] = useState([]);
    const [specialties, setSpecialties] = useState([]);

    useEffect(() => {
        fetchAppointments();
        fetchSpecialities();
    }, []);

    const fetchAppointments = async () => {
        try {
            const data = await appointmentService.getAllAppointments();
            setRequests(data);
        } catch (error) {
            console.error('Error fetching appointments:', error);
        }
    };

    const fetchDoctors = async (startTime, duration) => {
        try {
            const data = await appointmentService.getAvailableDoctors(startTime, duration);
            setDoctors(data);
        } catch (error) {
            console.error('Error fetching doctors:', error);
        }
    };

    const fetchSpecialities = async () => {
        try {
            const data = await appointmentService.getSpecialities();
            setSpecialties(data);
        } catch (error) {
            console.error('Error fetching specialities:', error);
        }
    };

    const handleRequestClick = (request) => {
        setSelectedRequest(request);
    };

    useEffect(() => {
        if (selectedRequest && duration && appointmentDate && appointmentTime) {
            const startTime = new Date(`${appointmentDate}T${appointmentTime}:00`);
            fetchDoctors(startTime.toISOString(), duration);
        }
    }, [selectedRequest, duration, appointmentDate, appointmentTime]);

    const handleAssignDoctor = (doctor) => {
        setAssignedDoctors([...assignedDoctors, doctor]);
    };

    const handleApprove = async () => {
        const appointmentData = {
            appointmentId: selectedRequest.id,
            isAccepted: true,
            fechaRealizacion: new Date(`${appointmentDate}T${appointmentTime}:00`),
            duration: parseInt(duration, 10),
            doctorsId: assignedDoctors.map(doc => doc.id),
            specialties: [specialty],
            userId: selectedRequest.userId
        };

        try {
            const response = await appointmentService.approveAppointment(appointmentData);
            console.log('Appointment approved:', response);
        } catch (error) {
            console.error('Error approving appointment:', error);
        }
    };

    const handleReject = async () => {
        const appointmentData = {
            appointmentId: selectedRequest.id,
            isAccepted: false
        };

        try {
            const response = await appointmentService.approveAppointment(appointmentData);
            console.log('Appointment denied:', response);
        } catch (error) {
            console.error('Error denying appointment:', error);
        }
    };

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="w-1/3 border-r border-gray-300 p-4">
                <h2 className="text-xl font-bold mb-4">Solicitudes</h2>
                <p className="font-semibold mb-5 text-gray-500">De click en las solicitudes que desea Consultar</p>
                <ul className="space-y-2">
                    {requests.map(request => (
                        <li
                            key={request.id}
                            className="p-2 bg-white rounded shadow cursor-pointer hover:bg-gray-200"
                            onClick={() => handleRequestClick(request)}
                        >
                            {request.reason} - {request.solicitadaDate ? new Date(request.solicitadaDate).toLocaleString() : 'N/A'}
                        </li>
                    ))}
                </ul>
            </div>

            <div className="w-2/3 p-4">
                {selectedRequest && (
                    <>
                        <h2 className="text-xl font-bold mb-4">Detalle de la Solicitud</h2>
                        <p className="mb-2"><strong>Razón:</strong> {selectedRequest.reason}</p>
                        <p className="mb-4"><strong>Fecha:</strong> {selectedRequest.solicitadaDate ? new Date(selectedRequest.solicitadaDate).toLocaleString() : 'N/A'}</p>
                        <label className="block mb-4">
                            Fecha de Realización:
                            <input
                                type="date"
                                value={appointmentDate}
                                onChange={(e) => setAppointmentDate(e.target.value)}
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>
                        <label className="block mb-4">
                            Hora de la Consulta:
                            <input
                                type="time"
                                value={appointmentTime}
                                onChange={(e) => setAppointmentTime(e.target.value)}
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>
                        <label className="block mb-4">
                            Especialidad:
                            <select
                                value={specialty}
                                onChange={(e) => setSpecialty(e.target.value)}
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            >
                                <option value="">Seleccione una especialidad</option>
                                {specialties.map(spec => (
                                    <option key={spec.id} value={spec.name}>{spec.name}</option>
                                ))}
                            </select>
                        </label>
                        <label className="block mb-4">
                            Duración (minutos):
                            <input
                                type="number"
                                value={duration}
                                onChange={(e) => setDuration(e.target.value)}
                                className="mt-1 block w-full p-2 border border-gray-300 rounded"
                            />
                        </label>

                        <h3 className="text-lg font-bold mb-2">Doctores Disponibles</h3>
                        <ul className="space-y-2 mb-4">
                            {doctors.map(doctor => (
                                <li key={doctor.id} className="p-2 bg-white rounded shadow">
                                    {doctor.name} - {doctor.specialty} ({doctor.available ? 'Disponible' : 'No Disponible'})
                                    {doctor.available && (
                                        <button
                                            onClick={() => handleAssignDoctor(doctor)}
                                            className="ml-4 bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-600"
                                        >
                                            Asignar
                                        </button>
                                    )}
                                </li>
                            ))}
                        </ul>

                        <h3 className="text-lg font-bold mb-2">Doctores Asignados</h3>
                        <ul className="space-y-2">
                            {assignedDoctors.map(doctor => (
                                <li key={doctor.id} className="p-2 bg-white rounded shadow">
                                    {doctor.name} - {doctor.specialty}
                                </li>
                            ))}
                        </ul>

                        <div className="mt-4">
                            <button onClick={handleApprove} className="mr-4 bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">Aprobar Solicitud</button>
                            <button onClick={handleReject} className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">Rechazar Solicitud</button>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default AppointmentApprovalPage;
