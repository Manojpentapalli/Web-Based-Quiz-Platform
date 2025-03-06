import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  Title,
  Tooltip
} from "chart.js";
import React from "react";
import { Bar } from "react-chartjs-2";
import { useNavigate } from 'react-router-dom'; // Import useNavigate

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const ViewProgress = () => {
  const navigate = useNavigate(); // Initialize useNavigate

  const backendData = {
    "java-test-2": [59.0],
    "java-test-3": [42.0],
    "java-test-1": [50.0],
    "cpp-test-3": [55.0],
    "cpp-test-1": [47.0],
    "cpp-test-2": [50.0],
    "mysql-test-1": [55.0],
    "mysql-test-3": [53.0],
    "mysql-test-2": [46.0],
  };

  // Organize data by subject
  const subjects = ["java", "cpp", "mysql"];
  const testsPerSubject = 3; // Each subject has 3 tests (test-1, test-2, test-3)
  
  const chartData = () => {
    const labels = subjects; // Subject names on the x-axis
    const datasets = [];

    // Define colors for each test
    const testColors = [
      "rgba(255, 99, 132, 0.8)",  // Test 1 - Red
      "rgba(54, 162, 235, 0.8)",  // Test 2 - Blue
      "rgba(75, 192, 192, 0.8)",  // Test 3 - Green
    ];

    // Create a dataset for each test (test-1, test-2, test-3)
    for (let i = 1; i <= testsPerSubject; i++) {
        const testData = subjects.map(
          (subject) => backendData[subject + '-test-' + i]?.[0] || 0 // Get score or 0 if not available

      );

      datasets.push({
        label: `Test ${i}`,
        data: testData,
        backgroundColor: testColors[i - 1],
      });
    }

    return {
      labels: labels,  // Subject names on the x-axis
      datasets: datasets,
    };
  };

  const options = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
        max: 100,
      },
    },
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Test Progress by Subject",
      },
    },
  };

  return (
    <div style={{ width: "800px", margin: "0 auto" }}>
      <h2>Test Progress by Subject</h2>
      <Bar options={options} data={chartData()} />
      <button onClick={() => navigate('/StudentDashboard')} className="back-btn">Back to Dashboard</button> {/* Add button */}
    </div>
  );
};

export default ViewProgress;