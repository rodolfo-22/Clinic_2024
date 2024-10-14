import React from 'react'
import { Route, Routes } from 'react-router-dom'
import CrearCita from './CrearCita'
import Navbar from '../../components/Navbar';
import VerPrescripciones from './VerPrescripciones';
import HisotialMedico from './HistorialMedico';

const PacienteRoutes = () => {
    const MenuItems = [
        { label: 'Crear cita medica', url: '/paciente' },
        //{ label: 'Historial medico', url: '/paciente/history' },
        //{ label: 'Prescripciones Vigentes', url: '/paciente/pres' },
    ];
    return (
        <>
        <Navbar menuItems={MenuItems}/>
        <Routes>
            <Route path="/" element={<CrearCita />} />
            {/*<Route path="/pres" element={<VerPrescripciones/>} />
            <Route path="/history" element={<HisotialMedico/>} />*/}
        </Routes>
        </>
    )
}

export default PacienteRoutes
