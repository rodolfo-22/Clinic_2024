import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from '../public/HomePage';
import LoginPage from '../components/Login';
import RegisterPage from '../components/Register';
import PacienteRoutes from '../pages/paciente/PacienteRoutes';
import DoctorRoutes from '../pages/doctor/DoctorRoutes';
import AsisitenteRoutes from '../pages/asistente/AsisitenteRoutes';
import AdminPage from '../pages/AdminRoles';
import PrivateRoute from '../utils/PrivateRoute';

const AppRouter = () => {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />


                <Route path='/paciente/*' element={
                <PrivateRoute>
                    <PacienteRoutes />
                </PrivateRoute>
                } />

                <Route path="/admin" element={
                    <PrivateRoute roles={['Administrador']}>
                        <AdminPage />
                    </PrivateRoute>
                } />

                <Route path="/asistente/*" element={
                    <PrivateRoute roles={['Asistente']}>
                        <AsisitenteRoutes />
                    </PrivateRoute>
                } />

                <Route path="/doctor/*" element={
                    <PrivateRoute roles={['Doctor']}>
                        <DoctorRoutes />
                    </PrivateRoute>
                } />

            </Routes>
        
        </Router>
    )
}

export default AppRouter
