import React, { useState, useEffect } from 'react';
import axios from 'axios';
import userService from '../services/userService';

const AdminRoles = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [role, setRole] = useState('');

    useEffect(() => {
        // Fetch users from the backend
        userService.getUsers()
            .then(data => setUsers(data))
            .catch(error => console.error('Error fetching users:', error));
    }, []);

    const handleUserClick = (user) => {
        setSelectedUser(user);
        setRole(''); // Clear role selection
    };

    const handleRoleChange = (e) => {
        setRole(e.target.value);
    };

    const handleSaveRole = () => {
        const token = localStorage.getItem('token'); // Obtener el token desde el localStorage
        axios.post('http://localhost:8080/config/user-role', {
            identifier: selectedUser.username, // Or use email if that's the identifier
            role: role
        }, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            // Actualizar el estado del usuario seleccionado con el nuevo rol
            setSelectedUser(prevUser => ({
                ...prevUser,
                roles: [...prevUser.roles, role]
            }));
            console.log('Role updated:', response.data);
        }).catch(error => {
            console.error('Error updating role:', error);
        });
    };

    const handleRemoveRole = (roleToRemove) => {
        const token = localStorage.getItem('token'); // Obtener el token desde el localStorage
        axios.delete('http://localhost:8080/config/user-role', {
            data: {
                identifier: selectedUser.username, // Or use email if that's the identifier
                role: roleToRemove
            },
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            // Actualizar el estado del usuario seleccionado eliminando el rol
            setSelectedUser(prevUser => ({
                ...prevUser,
                roles: prevUser.roles.filter(r => r !== roleToRemove)
            }));
            console.log('Role removed:', response.data);
        }).catch(error => {
            console.error('Error removing role:', error);
        });
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-gray-100 py-10">
            <h1 className="text-3xl font-bold text-black mb-5">Administrar Roles de Usuarios</h1>
            
            <div className="flex w-full max-w-4xl mt-5">
                <div className="w-1/2 border-r border-gray-300 p-4">
                    <h2 className="text-xl font-bold mb-4">Lista de Usuarios</h2>
                    <ul className="space-y-2">
                        {users.map(user => (
                            <li 
                                key={user.id} 
                                className="p-4 bg-white rounded shadow cursor-pointer hover:bg-gray-200" 
                                onClick={() => handleUserClick(user)}
                            >
                                {user.username} - {user.roles.join(', ')}
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="w-1/2 p-4">
                    {selectedUser && (
                        <>
                            <h2 className="text-xl font-bold mb-4">Asignar Rol</h2>
                            <p className="mb-4"><strong>Usuario:</strong> {selectedUser.username}</p>
                            <label className="block mb-4">
                                Rol:
                                <select 
                                    value={role} 
                                    onChange={handleRoleChange} 
                                    className="mt-1 block w-full p-2 border border-gray-300 rounded"
                                >
                                    <option value="">Seleccione un rol</option>
                                    <option value="Paciente">Paciente</option>
                                    <option value="Doctor">Doctor</option>
                                    <option value="Assistant">Assistant</option>
                                </select>
                            </label>
                            <button 
                                onClick={handleSaveRole} 
                                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                            >
                                Guardar Rol
                            </button>
                            <h2 className="text-xl font-bold mb-4 mt-4">Eliminar Rol</h2>
                            <ul className="space-y-2">
                                {selectedUser.roles.map(role => (
                                    <li key={role} className="flex justify-between items-center p-4 bg-white rounded shadow">
                                        {role}
                                        <button 
                                            onClick={() => handleRemoveRole(role)} 
                                            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                                        >
                                            Quitar Rol
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default AdminRoles;
