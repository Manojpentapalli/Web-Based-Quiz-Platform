import axios from "axios";
import React, { useCallback, useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import "../styles/QuizPage.css";
import CelebrationModal from "./CelebrationModal";

const totalDuration = 30 * 60; // 1 minute in seconds

const Quiz = () => {
    const [questions, setQuestions] = useState([]);
    const [number, setNumber] = useState(0);
    const [timeLeft, setTimeLeft] = useState(totalDuration);
    const [selectedOptions, setSelectedOptions] = useState([]);
    const [markedForReview, setMarkedForReview] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [score, setScore] = useState(0);
    const [isTimeUp, setIsTimeUp] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { testName } = useParams();
    const location = useLocation();
    const { userId, testId } = location.state || {};

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/quiznow/tests/${testName}`);

                if (Array.isArray(response.data)) {
                    setQuestions(response.data);
                    setSelectedOptions(new Array(response.data.length).fill(null));
                    setMarkedForReview(new Array(response.data.length).fill(false));
                } else {
                    setError("Failed to fetch questions. Invalid response format.");
                }
            } catch (error) {
                setError("Failed to fetch quiz details. Please try again.");
            }
        };

        if (testName) {
            fetchQuestions();
        } else {
            setError("Invalid test name.");
        }
    }, [testName]);

    const handleOptionChange = (event) => {
        const updatedOptions = [...selectedOptions];
        updatedOptions[number] = event.target.value;
        setSelectedOptions(updatedOptions);
    };

    const goToQuestion = (index) => {
        setNumber(index);
    };

    const toggleReview = () => {
        const updatedReview = [...markedForReview];
        updatedReview[number] = !updatedReview[number];
        setMarkedForReview(updatedReview);
    };

    const unmarkQuestion = () => {
        const updatedOptions = [...selectedOptions];
        updatedOptions[number] = null;
        setSelectedOptions(updatedOptions);
    };

    const nextQuestion = () => {
        if (number < questions.length - 1) {
            setNumber(number + 1);
        }
    };

    const previousQuestion = () => {
        if (number > 0) {
            setNumber(number - 1);
        }
    };

    const handleSubmit = useCallback(async () => {
        let calculatedScore = 0;

        questions.forEach((question, index) => {
            if (selectedOptions[index] === question.correctOption) {
                calculatedScore += 1;
            }
        });

        setScore(calculatedScore);
        setShowModal(true);

        try {
            await axios.post("http://localhost:8080/quiznow/testmarks/submit", {
                test_id: testId,
                student_id: userId,
                marks_obtained: calculatedScore,
            });
        } catch (error) {
            console.error("Error submitting test marks:", error);
        }
    }, [selectedOptions, questions, testId, userId]);

    const closeModal = () => {
        setShowModal(false);
        navigate("/StudentDashboard");
    };

    useEffect(() => {
        if (timeLeft > 0) {
            const timer = setInterval(() => {
                setTimeLeft((prevTime) => prevTime - 1);
            }, 1000);
            return () => clearInterval(timer);
        } else {
            setIsTimeUp(true);
            if (!showModal) {
                handleSubmit();
            }
        }
    }, [timeLeft, showModal, handleSubmit]);

    const formatTime = (seconds) => {
        const minutes = Math.floor(seconds / 60);
        return `${minutes}:${seconds % 60 < 10 ? "0" : ""}${seconds % 60}`;
    };

    if (error) return <div>{error}</div>;
    if (!questions.length) return <div>Loading questions...</div>;

    return (
        <div className="quiz-container">

            {/* Left Panel */}
            <div className="left-panel">
                <div className="question-grid">
                    {questions.map((_, index) => (
                        <button
                            key={index}
                            className={`grid-btn ${selectedOptions[index] ? "answered" : "unanswered"} ${markedForReview[index] ? "review" : ""}`}
                            onClick={() => goToQuestion(index)}
                        >
                            {index + 1}
                        </button>
                    ))}
                </div>


                <div className="status-panel">
                    <p>Answered: {selectedOptions.filter((opt) => opt !== null).length}</p>
                    <p>Unanswered: {selectedOptions.filter((opt) => opt === null).length}</p>
                    <p>Marked for Review: {markedForReview.filter((mark) => mark).length}</p>
                </div>
            </div>

            {/* Center Content */}
            <div className="question-content">
                <div className="timer">Time Left: {formatTime(timeLeft)}</div>
                <p>Question {number + 1}</p>
                <h2>{questions[number]?.question || "Question not available"}</h2>

                <div className="options-container">
                    {["option1", "option2", "option3", "option4"].map((optionKey, index) => (
                        <label key={index} className="option-label">
                            <input
                                type="radio"
                                value={questions[number]?.[optionKey]}
                                checked={selectedOptions[number] === questions[number]?.[optionKey]}
                                onChange={handleOptionChange}
                                disabled={isTimeUp}
                            />
                            <span className="option-text">{String.fromCharCode(65 + index)}. {questions[number]?.[optionKey]}</span>
                        </label>
                    ))}
                </div>

                <div className="nav-buttons">
                    <button className="quiz-btn" onClick={unmarkQuestion} disabled={isTimeUp}>
                        Unmark Question
                    </button>
                    <button className="quiz-btn" onClick={toggleReview} disabled={isTimeUp}>
                        {markedForReview[number] ? "Unmark Review" : "Mark for Review"}
                    </button>
                    <button className="quiz-btn" onClick={previousQuestion} disabled={number === 0 || isTimeUp}>
                        Previous
                    </button>
                    <button className="quiz-btn" onClick={nextQuestion} disabled={number === questions.length - 1 || isTimeUp}>
                        Next
                    </button>
                    <button className="quiz-btn" onClick={handleSubmit} disabled={isTimeUp}>
                        Submit
                    </button>
                </div>

                {showModal && <CelebrationModal score={score} totalQuestions={questions.length} onClose={closeModal} />}
            </div>

        </div>
    );
};

export default Quiz;

