import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/StudentDashboard.css';
import QuizNow from '../Images/QuizNowLogo1.png';

function Dashboard() {
  const [tests, setTests] = useState([]);
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId'); // ✅ Fetch userId from localStorage

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

  const getLatestTest = (subject) => {
    const subjectTests = tests.filter(test => test.testName.startsWith(subject));
    if (subjectTests.length === 0) return null;
    return subjectTests[subjectTests.length - 1];
  };

  const handleStartTest = (subject) => {
    const latestTest = getLatestTest(subject);
    if (!latestTest) return;

    navigate('/StudentInstruction', {
      state: {
        userId,  // ✅ Passing userId when starting test
        testId: latestTest.testId,
        testName: subject,
        duration: latestTest.duration
      }
    });
  };

  const handleLogout = () => {
    localStorage.removeItem('userId'); // Clear user data from local storage
    navigate('/'); // Navigate back to the login/register page
  };

  const uniqueSubjects = [...new Set(tests.map(test => test.testName.split('-')[0]))];

  return (
    <div className="dashboard-container">
      <header className="Header">
  <img src={QuizNow} alt="logo" />
  <h1><b>Quiz Now</b></h1>
  <button className="logout-btn" onClick={handleLogout}>Logout</button> 
</header>

      <div className="left-panel">
        <h3 className = "user-id"> user ID : {userId}</h3>
        <div  className = "button-container">
        <h3 className="btn btn-leaderboard" onClick={() => navigate('/StudentLeaderboard', { state: { userId } })}>Leaderboard</h3>
        <h3 className="btn btn-progress" onClick={() => navigate('/view-progress', { state: { userId } })}>View Progress</h3> {/* Update navigation */}
        </div>  
      </div>

      <div className="right-panel">
        <div className="grid-container">
          {Array.from({ length: Math.ceil(uniqueSubjects.length / 3) }, (_, rowIndex) => (
            <div className="grid-row" key={rowIndex}>
              {uniqueSubjects.slice(rowIndex * 3, rowIndex * 3 + 3).map((subject) => {
                const latestTest = getLatestTest(subject);
                return (
                  <div className="grid-col-3" key={subject} onClick={() => handleStartTest(subject)}>
                    <h2>{subject}</h2>
                    {latestTest ? (
                      <div className="test-details-container">
                        <p className="test-details">Duration: {latestTest.duration} mins</p>
                        <p className="test-details">Total: {latestTest.totalMarks} marks</p>
                      </div>
                    ) : (
                      <p>No tests available</p>
                    )}
                  </div>
                );
              })}
            </div>
          ))}
        </div>
      </div>
       {/* Footer */}
       <footer className="Footer">
        <p><b>All rights reserved &copy; 2025</b></p>
      </footer>
    </div>
  );
}

export default Dashboard;