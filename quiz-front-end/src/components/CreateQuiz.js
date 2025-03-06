import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/CreateQuiz.css";
import QuizNowLogo from "../Images/QuizNowLogo1.png";

function CreateQuiz() {
  const [testName, setTestName] = useState("");
  const [duration, setDuration] = useState("");
  const [totalMarks, setTotalMarks] = useState("");
  const [file, setFile] = useState(null);
  const navigate = useNavigate();

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!testName || !duration || !totalMarks || !file) {
      alert("All fields are required!");
      return;
    }

    const formData = new FormData();
    formData.append("testId", Math.floor(Math.random() * 10000)); // Generate random testId
    formData.append("testName", testName);
    formData.append("duration", duration);
    formData.append("totalMarks", totalMarks);
    formData.append("file", file);

    try {
      const response = await axios.post("http://localhost:8080/quiznow/tests/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      alert(response.data.message);
      navigate("/CreateQuiz");
    } catch (error) {
      console.error("Error uploading quiz:", error);
      alert("Failed to create quiz. Please try again.");
    }
  };

  return (
    <div className="create-quiz">
      <header>
        <img src={QuizNowLogo} alt="QuizNow Logo" />
        <h1>Create Quiz</h1>
      </header>

      <main>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Test Name:</label>
            <input type="text" value={testName} onChange={(e) => setTestName(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Duration (in minutes):</label>
            <input type="number" value={duration} onChange={(e) => setDuration(e.target.value)} required />
          </div>


          <div className="form-group">
            <label>Total Marks:</label>
            <input type="number" value={totalMarks} onChange={(e) => setTotalMarks(e.target.value)} required />
          </div>

          <p>
            <i>
              Format: Question Number, "Question", "Option1", "Option2", "Option3", "Option4", "Correct-Answer" <br />
              Example: (1,"What is the capital of India?","New Delhi","Mumbai","Kolkata","Delhi","4")
            </i>
          </p>

          <div className="form-group">
            <label>Upload Questions (Text File):</label>
            <input type="file" accept=".txt" onChange={handleFileChange} required />
          </div>

          <div className="form-actions">
            <button type="submit">Create Quiz</button>
            <button type="button" onClick={() => navigate("/AdminDashboard")}>Cancel</button>
          </div>
        </form>
      </main>
    </div>
  );
}

export default CreateQuiz;
