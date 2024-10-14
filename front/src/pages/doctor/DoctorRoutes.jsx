import Navbar from "../../components/Navbar";
import { Route, Routes } from 'react-router-dom';
import ListCitas from "./ListCitas";
import AddHistory from "../doctor/AddHistory";
import PrescriptionList from "./PrescriptionList";
import AddPrescription from "./AddPrescription";
import FinishCita from "./FinishCita";

const DoctorRoutes = () => {
    const MenuItems = [
        { label: 'Cita medica', url: '/doctor' },
        { label: 'Revisar cita medica', url: '/doctor/list' },
        { label: 'Añadir historial medico', url: '/doctor/history' },
        { label: 'Prescripciones Vigentes', url: '/doctor/pres' },
        { label: 'Añadir prescripciones a cita medica', url: '/doctor/add' },
        
    ];
    return (
        <>
        <Navbar menuItems={MenuItems}/>
        <Routes>
            <Route path="/list" element={<ListCitas />} />
            <Route path="/history" element={<AddHistory />} />
            <Route path="/pres" element={<PrescriptionList/>} />
            <Route path="/add" element={<AddPrescription />} />
            <Route path="/" element={<FinishCita/>} />

        </Routes>
        </>
    )
}

export default DoctorRoutes
