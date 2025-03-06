import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Leaderboard.css";

const Leaderboard = () => {
  const [leaderboardData, setLeaderboardData] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/quiznow/testmarks/leaderboard") // Fetch from backend
      .then((response) => {
        setLeaderboardData(response.data);
      })
      .catch((error) => {
        console.error("Error fetching leaderboard data:", error);
      });
  }, []);

  const handleBackToDashboard = () => {
    navigate("/AdminDashboard"); // Adjust according to your route
  };

  if (leaderboardData.length === 0) {
    return <div className="leaderboard"><h2>Loading Leaderboard...</h2></div>;
  }

  // Sort data based on average marks (descending)
  const sortedData = [...leaderboardData].sort((a, b) => b.averageMarks - a.averageMarks);

  // Extract podium and remaining ranks
  const first = sortedData[0];
  const second = sortedData[1];
  const third = sortedData[2];
  const rest = sortedData.slice(3);

  return (
    <div className="leaderboard">
      <h1>Leaderboard</h1>

      <div className="podium">
        <div className="second-place">
          <h3>{second?.studentName || "N/A"}</h3>
          <p>{second?.averageMarks?.toFixed(2) || "-"}</p>
        </div>
        <div className="first-place">
          <h3>{first?.studentName || "N/A"}</h3>
          <p>{first?.averageMarks?.toFixed(2) || "-"}</p>
        </div>
        <div className="third-place">
          <h3>{third?.studentName || "N/A"}</h3>
          <p>{third?.averageMarks?.toFixed(2) || "-"}</p>
        </div>
      </div>

      <table className="leaderboard-table">
        <thead>
          <tr>
            <th>Rank</th>
            <th>Student Name</th>
            <th>Average Marks</th>
          </tr>
        </thead>
        <tbody>
          {rest.map((entry, index) => (
            <tr key={index}>
              <td>{index + 4}</td>
              <td>{entry.studentName}</td>
              <td>{entry.averageMarks.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <button onClick={handleBackToDashboard}>Back to Dashboard</button>
    </div>
  );
};

export default Leaderboard;
