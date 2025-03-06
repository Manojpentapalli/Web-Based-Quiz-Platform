import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/AdminDashboard.css'; // Ensure the path is correct
import QuizLogo from '../Images/QuizNowLogo1.png'; // Ensure the path is correct

const AdminDashboard = ({ onLogout }) => {
    const navigate = useNavigate();
    const [tests, setTests] = useState([]); // State to store test details

    useEffect(() => {
        const fetchTests = async () => {
            try {
                const response = await axios.get('http://localhost:8080/quiznow/testdetails/getTestDetails');
                setTests(response.data);
            } catch (error) {
                console.error('Error fetching test details:', error);
            }
        };
        fetchTests();
    }, []);

    const handleLogout = () => {
        navigate('/'); // Navigate back to the login/register page
    };

    return (
        <div className="dashboard">
            {/* Header Section */}
            <header>
                <img src={QuizLogo} alt="QuizNow Logo" />
                <div className="manage-account">
                    <i className="fa-solid fa-user"></i>
                    <button className="logout-btn" onClick={handleLogout}>Logout</button>
                </div>
            </header>

            {/* Main Content */}
            <main>
                {/* Sidebar Navigation */}
                <section className="left-panel">
                    <ul>
                        <li>
                            <a href='/CreateQuiz'><button><i className="fa-solid fa-plus"></i> Create Quiz</button></a>
                        </li>
                        <li>
                            <a href='/AdminLeaderboard'><button><i className="fa-solid fa-trophy"></i> Leaderboard</button></a>
                        </li>
                        <li>
                            <a href='/result-analysis'><button><i className="fa-solid fa-chart-pie"></i> Result Analysis</button></a>
                        </li>
                    </ul>
                </section>

                {/* Right Panel Content */}
                <section className="right-panel">
                    <h2>Manage Tests</h2>
                    <table className="test-table">
                        <thead>
                            <tr>
                                <th>Test</th>
                                <th>Total Marks</th>
                                <th>Duration</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tests.map((test, index) => (
                                <tr key={index}>
                                    <td>{test.testName}</td>
                                    <td>{test.totalMarks}</td>
                                    <td>{test.duration} min</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            </main>
        </div>
    );
};

export default AdminDashboard;
