import { useNavigate } from "react-router-dom";
import React, { useState } from "react";

const Navbar = ({ menuItems }) => {
    const [pictureAbierto, setPictureAbierto] = useState(false);
    const [menuAbierto, setMenuAbierto] = useState(false);
    const togglePicture = () => {
        setPictureAbierto(!pictureAbierto);
    };
    const toggleMenu = () => {
        setMenuAbierto(!menuAbierto);
    };
    const navigate = useNavigate();

    const logOut = () => {
        localStorage.removeItem("access_token");
        localStorage.removeItem("role");
        navigate('/');
    };

    return (
        <>
            <nav className="bg-white border-gray-200 dark:bg-gray-900">
                <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                    <a href="home" className="flex items-center space-x-3 rtl:space-x-reverse">
                        <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
                            Ya merito
                        </span>
                    </a>

                    <div className="flex flex-row md:flex-col md:items-end md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                        <button
                            type="button"
                            className="flex items-center text-sm md:bg-gray-800 rounded-full md:me-0 focus:ring-4 focus:ring-gray-300 dark:focus:ring-gray-600"
                            id="user-picture-button"
                            aria-expanded={pictureAbierto ? "true" : "false"}
                            onClick={togglePicture}
                            data-dropdown-toggle="user-dropdown"
                            data-dropdown-placement="bottom"
                        >
                        </button>

                        {pictureAbierto && (
                            <div className="z-50 absolute right-0 text-base m-10 list-none bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600">
                                <ul className="py-1" aria-labelledby="user-menu-button">
                                    <li>
                                        <button
                                            onClick={logOut}
                                            className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                                        >
                                            Sign out
                                        </button>
                                    </li>
                                </ul>
                            </div>
                        )}

                        <button
                            data-collapse-toggle="navbar-user"
                            type="button"
                            className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
                            aria-expanded={menuAbierto ? "true" : "false"}
                            onClick={toggleMenu}
                        >
                            <svg
                                className="w-5 h-5"
                                aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 17 14"
                            >
                                <path
                                    stroke="currentColor"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="M1 1h15M1 7h15M1 13h15"
                                />
                            </svg>
                        </button>
                        {menuAbierto && (
                            <div className="z-50 absolute right-0 text-base m-11 list-none bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600 md:hidden">
                                <ul className="py-1" aria-labelledby="user-menu-button">
                                    {menuItems.map((menuItem, index) => (
                                        <li key={index}>
                                            <a
                                                href={menuItem.url}
                                                className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white"
                                            >
                                                {menuItem.label}
                                            </a>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </div>

                    <div
                        className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1"
                        id="navbar-user"
                    >
                        <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                            {menuItems.map((menuItem, index) => (
                                <li key={index}>
                                    <a
                                        href={menuItem.url}
                                        className=" text-xl block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                                    >
                                        {menuItem.label}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    );
};

export default Navbar;
