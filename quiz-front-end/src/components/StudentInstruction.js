import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../styles/Instruction.css';

function StudentInstruction() {
    const navigate = useNavigate();
    const location = useLocation();

    // Retrieve the test details passed from the Dashboard page
    const { testName, questions, userId, testId } = location.state || {}

    const startQuiz = () => {
        navigate(`/quiznow/tests/${testName}`, {state : {questions, userId, testId}});
    };

    return (
        <div className="test-instructions">
            <h2>Quiz Instructions</h2>
            <h2>User ID : {userId}</h2>
            <h2>Test ID : {testId}</h2>
            <p>
                <strong>A. General information:</strong><br />
                1. The examination will comprise of Objective type Multiple Choice Questions (MCQs).<br />
                2. All questions are compulsory and each carries One mark.<br />
                3. The total number of questions, duration of examination, will be different based on the course.<br />
                4. The Subjects or topics covered in the exam will be as per the Syllabus.<br />
                5. There will be <strong>NO NEGATIVE MARKING</strong> for the wrong answers.<br />
            </p>
            <p>
                <strong>B. Information & Instructions:</strong><br />
                1. The examination does not require using any paper, pen, pencil, and calculator.<br />
                2. Every student will take the examination on a Laptop/Desktop/Smart Phone.<br />
                3. Each student will get questions and answers in different order selected randomly.<br />
                4. The students just need to click on the Correct option from the multiple choices given.<br />
            </p>
            <p className="important-note">
                <strong>Important:</strong> Do not click the <strong>"End Test"</strong> button unless you want to leave early.
            </p>
            <button className="start-quiz-btn" onClick={startQuiz}>
                Start Quiz
            </button>
        </div>
    );
}

export default StudentInstruction;
