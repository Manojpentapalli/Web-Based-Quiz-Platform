import React from "react";
import { useNavigate } from "react-router-dom";
import QuizNow from "../Images/QuizNowLogo1.png";
import '../styles/Header.css';

function Header() {
  const navigate = useNavigate();

  const backToLoginPage = () => {
    navigate("/");
  };

  return (
    <header className="header">
    <img src={QuizNow} alt="QuizNow" className="logo" />
    <h1 className="title">Quiz Now</h1>
    <button className="logout-btn" onClick={backToLoginPage}>Logout</button>
    </header>

  );
}

export default Header;