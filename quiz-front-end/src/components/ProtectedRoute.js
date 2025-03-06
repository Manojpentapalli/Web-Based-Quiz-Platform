import React from 'react';
import { Navigate } from 'react-router-dom';

// Mock authentication function
const isAuthenticated = () => {
    // Replace with actual authentication logic
    return localStorage.getItem('userRole') !== null;
};

const ProtectedRoute = ({ children }) => {
    return isAuthenticated() ? children : <Navigate to="/" />;
};

export default ProtectedRoute;
