import Navbar from "../../components/Navbar";
import { Route, Routes } from 'react-router-dom';
import CheckCitas from './CheckCitas';
import AddHistory from './AddHistory';

const AsisitenteRoutes = () => {
    const MenuItems = [
        { label: 'Revisar cita medica', url: '/asistente' },
        { label: 'Añadir historial', url: '/asistente/history' },
    
    ];
    return (
        <>
        <Navbar menuItems={MenuItems}/>
        <Routes>
            <Route path="/" element={<CheckCitas />} />
            <Route path="/history" element={<AddHistory />} />

        </Routes>
        </>
    )
}

export default AsisitenteRoutes
