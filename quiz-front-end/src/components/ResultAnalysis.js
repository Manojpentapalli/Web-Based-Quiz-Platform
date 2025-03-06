import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/ResultAnalysis.css'; // Import the CSS file

function ResultAnalysis() {
  const [data, setData] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const response = [
        { testName: "Java", studentName: "Gagan", marksObtained: 29.0 },
        { testName: "Java", studentName: "Manoj", marksObtained: 17.0 },
        { testName: "MySQL", studentName: "Mudit Kumar", marksObtained: 25.0 },
        { testName: "C++", studentName: "Utkarsh", marksObtained: 13.0 },
        { testName: "Mongo DB", studentName: "Vashu Choudhary", marksObtained: 29.0 },
        { testName: "Mathemathics", studentName: "Varad", marksObtained: 27.0 },
        { testName: "Advanced Mathematics", studentName: "Aviral", marksObtained: 25.0 },
        { testName: "Python", studentName: "Shraddha", marksObtained: 23.0 },
        { testName: "Java", studentName: "Bob Smith", marksObtained: 19.0 },
        { testName: "Python", studentName: "Frank Wilson", marksObtained: 17.0 },
        { testName: "Mathematics", studentName: "Isla Carter", marksObtained: 25.0 },
        { testName: "C++", studentName: "Ella Scott", marksObtained: 33.0 },
      ];
      setData(response);
    };

    fetchData();
  }, []);

  // Group data by test name
  const groupedData = data.reduce((acc, item) => {
    if (!acc[item.testName]) {
      acc[item.testName] = [];
    }
    acc[item.testName].push(item);
    return acc;
  }, {});

  // Logout function
  const handleLogout = () => {
    alert("Logged out successfully!");
    navigate('/');
  };

  return (
    <div className="result-analysis">
      <div className="result-analysis-container">
        <div className="header">
          <h2>Result Analysis</h2>
          <button className="logout-btn" onClick={handleLogout}>Logout</button>
        </div>

        {Object.keys(groupedData).map((testName) => (
          <div key={testName} className="test-section">
            <h2 className="test-heading">{testName}</h2>
            <table className="result-table">
              <thead>
                <tr>
                  <th>Student Name</th>
                  <th>Marks Obtained</th>
                </tr>
              </thead>
              <tbody>
                {groupedData[testName].map((item, index) => (
                  <tr key={index}>
                    <td>{item.studentName}</td>
                    <td>{item.marksObtained}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ))}

        <a href='/AdminDashboard' className="dashboard-button">
          Go to Dashboard
        </a>
      </div>
    </div>
  );
}

export default ResultAnalysis;