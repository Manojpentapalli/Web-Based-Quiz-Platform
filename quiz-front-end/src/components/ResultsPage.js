import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../styles/ResultsPage.css';

function ResultsPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const { userId, testName, answers } = location.state || {};

    // Calculate the score or any other result processing logic here

    return (
        <div className="results-page">
            <h2>Quiz Results</h2>
            <p>User ID: {userId}</p>
            <p>Test Name: {testName}</p>
            <p>Answers: {JSON.stringify(answers)}</p>
            <button onClick={() => navigate('/')}>Go to Dashboard</button>
        </div>
    );
}

export default ResultsPage;