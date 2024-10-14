import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarAlt, faUserMd, faMapMarkerAlt, faHeartbeat, faUserFriends } from '@fortawesome/free-solid-svg-icons';
import bg from '../assets/bg.webp';

const HomePage = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/login');
    };

    const handleRegister = () => {
        navigate('/register');
    };

    return (
        <div className="relative w-full h-screen bg-cover bg-center" style={{ backgroundImage: `url(${bg})` }}>
            <div className="absolute inset-0 bg-black bg-opacity-50 flex flex-col justify-center items-center text-center text-white">
                <h1 className="text-5xl font-extrabold mb-4 shadow-lg">Because of you, we never stop.</h1>
                <p className="text-2xl mb-8 shadow-lg">We break boundaries. We pioneer solutions. We make the impossible possible.</p>
                <div className="flex justify-center space-x-4 mb-8">
                    <div className="flex flex-col items-center">
                        <div className="bg-blue-500 p-4 rounded-full mb-2">
                            <FontAwesomeIcon icon={faCalendarAlt} className="text-white text-3xl" />
                        </div>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="bg-yellow-500 p-4 rounded-full mb-2">
                            <FontAwesomeIcon icon={faUserMd} className="text-white text-3xl" />
                        </div>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="bg-blue-700 p-4 rounded-full mb-2">
                            <FontAwesomeIcon icon={faMapMarkerAlt} className="text-white text-3xl" />
                        </div>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="bg-teal-500 p-4 rounded-full mb-2">
                            <FontAwesomeIcon icon={faHeartbeat} className="text-white text-3xl" />
                        </div>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="bg-purple-500 p-4 rounded-full mb-2">
                            <FontAwesomeIcon icon={faUserFriends} className="text-white text-3xl" />
                        </div>
                    </div>
                </div>
                <div className="flex justify-center space-x-4">
                    <button 
                        onClick={handleLogin}
                        className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Login
                    </button>
                    <button 
                        onClick={handleRegister}
                        className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Register
                    </button>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
