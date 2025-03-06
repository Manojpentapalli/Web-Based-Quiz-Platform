import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import '../styles/UpdateProfile.css';

const UpdateProfile = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { user } = location.state || {}; // Retrieve user details passed from the previous page

    const [name, setName] = useState(user ? user.name : '');
    const [email, setEmail] = useState(user ? user.email : '');
    const [phoneNumber, setPhoneNumber] = useState(user ? user.phoneNumber : '');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleUpdateProfile = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.put(`http://localhost:8080/quiznow/update-profile/${user.id}`, {
                name,
                email,
                phoneNumber
            });
            setSuccess('Profile updated successfully');
            setError(null);
        } catch (error) {
            console.error('Error updating profile:', error);
            setError('Failed to update profile. Please try again later.');
            setSuccess(null);
        }
    };

    return (
        <div className="update-profile">
            <h2>Update Profile</h2>
            {error && <p className="error-message">{error}</p>}
            {success && <p className="success-message">{success}</p>}
            <form onSubmit={handleUpdateProfile}>
                <div className="form-group">
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone Number:</label>
                    <input
                        type="text"
                        id="phoneNumber"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                    />
                </div>
                <button type="submit" className="update-btn">Update Profile</button>
            </form>
            <button onClick={() => navigate(-1)} className="back-btn">Back</button>
        </div>
    );
};

export default UpdateProfile;