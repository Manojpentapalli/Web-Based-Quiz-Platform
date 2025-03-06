import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/loginRegisterPage.css";

const LoginRegisterPage = () => {
  const [isSignUp, setIsSignUp] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  // Handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "email") setEmail(value);
    if (name === "password") setPassword(value);
    if (name === "phoneNumber") setPhoneNumber(value);
    if (name === "name") setName(value);
  };

  // Toggle between Sign In and Sign Up forms
  const toggleSignUp = () => {
    setIsSignUp(true);
    setErrorMessage("");
  };

  const toggleSignIn = () => {
    setIsSignUp(false);
    setErrorMessage("");
  };

  // Handle Sign Up
  const handleSignUp = async (e) => {
    e.preventDefault();
    if (!name || !email || !phoneNumber || !password) {
      setErrorMessage("All fields are required");
      return;
    }

    const signupData = { name, email, phoneNumber, password };

    try {
      const response = await axios.post("http://localhost:8080/quiznow/register", signupData);
      console.log("Sign Up Successful:", response.data);
      setIsSignUp(false); // Switch to Sign In page after successful registration
    } catch (error) {
      console.error("Error during sign up:", error);
      setErrorMessage("Error during sign up, please try again");
    }
  };

  // Handle Sign In
  const handleSignIn = async (e) => {
    e.preventDefault();
    if (!email || !password) {
      setErrorMessage("Email and password are required");
      return;
    }

    const loginData = { email, password };

    try {
      const response = await axios.post("http://localhost:8080/quiznow/login", loginData);
      if (response.status === 200) {
        console.log("Login response:", response);

        // Extracting user details
        const { id, name, role } = response.data;

        // Storing user details in localStorage
        localStorage.setItem("userId", id);
        localStorage.setItem("userName", name);
        localStorage.setItem("userRole", role);

        // Redirect based on role
        if (role === "Admin") {
          navigate("/AdminDashboard", { state: { userId: id } });
        } else {
          navigate("/StudentDashboard", { state: { userId: id } });
        }
      }
    } catch (error) {
      console.error("Error during sign in:", error);
      setErrorMessage("Invalid email or password");
    }
  };

  return (
    <div className={`container ${isSignUp ? "right-panel-active" : ""}`} id="container">
      <div className="row">
        {/* Sign Up Container */}
        <div className="form-container sign-up-container">
          <form onSubmit={handleSignUp}>
            <h1>Create Account</h1>
            <input type="text" name="name" placeholder="Name" value={name} onChange={handleInputChange} />
            <input type="email" name="email" placeholder="Email" value={email} onChange={handleInputChange} />
            <input type="text" name="phoneNumber" placeholder="Phone Number" value={phoneNumber} onChange={handleInputChange} />
            <input type="password" name="password" placeholder="Password" value={password} onChange={handleInputChange} />
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <button type="submit">Sign Up</button>
            <p id="mobile_para">To keep connected with us, please login</p>
            <button type="button" className="ghost_mobile" onClick={toggleSignIn}>
              Sign In
            </button>
          </form>
        </div>

        {/* Sign In Container */}
        <div className="form-container sign-in-container">
          <form onSubmit={handleSignIn}>
            <input type="email" name="email" placeholder="Email" value={email} onChange={handleInputChange} />
            <input type="password" name="password" placeholder="Password" value={password} onChange={handleInputChange} />
            {/* <a href="/forget">Forgot your password?</a> */}
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <button type="submit">Sign In</button>
            <p id="mobile_para">Don't have an account? Sign up here !!</p>
            <button type="button" className="ghost_mobile" onClick={toggleSignUp}>
              Sign Up
            </button>
          </form>
        </div>

        {/* Overlay */}
        <div className="overlay-container">
          <div className="overlay">
            <div className="overlay-panel overlay-left">
              <h1>Welcome Back!</h1>
              <p>To keep connected with us please login with your info</p>
              <button className="ghost" onClick={toggleSignIn}>
                Sign In
              </button>
            </div>
            <div className="overlay-panel overlay-right">
              <h1>Hello, Quizzers!</h1>
              <p>Enter your details and <br />start your journey with us</p>
              <button className="ghost" onClick={toggleSignUp}>
                Sign Up
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginRegisterPage;
