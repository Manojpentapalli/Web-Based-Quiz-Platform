import React from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import AdminDashboard from './components/AdminDashboard';
import AdminLeaderboard from './components/AdminLeaderboard';
import CelebrationModal from './components/CelebrationModal';
import CreateQuiz from './components/CreateQuiz';
import Footer from './components/Footer';
import Header from './components/Header';
import LoginRegisterPage from './components/LoginRegisterPage';
import ProtectedRoute from './components/ProtectedRoute';
import QuizPage from './components/QuizPage';
import ResultAnalysis from './components/ResultAnalysis';
import StudentDashboard from './components/StudentDashboard';
import StudentInstruction from './components/StudentInstruction';
import StudentLeaderboard from './components/StudentLeaderboard';
import ResultsPage from './components/ResultsPage';
import ViewProgress from './components/ViewProgress'; // Import ViewProgress component

const App = () => {
  return (
    <Router>
      <Routes>
        {/* Public Route */}
        <Route path="/" element={<LoginRegisterPage />} />

        {/* Protected Routes */}
        <Route 
          path="/AdminDashboard" 
          element={<ProtectedRoute><AdminDashboard /></ProtectedRoute>} 
        />
        <Route 
          path="/StudentDashboard" 
          element={<ProtectedRoute><StudentDashboard /></ProtectedRoute>} 
        />
        <Route 
          path="/StudentInstruction" 
          element={<ProtectedRoute><StudentInstruction /></ProtectedRoute>} 
        />
        <Route 
          path="/quiznow/tests/:testName" 
          element={<ProtectedRoute><QuizPage /></ProtectedRoute>} 
        />
         <Route 
          path="/CelebrationModal" 
          element={<ProtectedRoute><CelebrationModal /></ProtectedRoute>} 
        />
        <Route 
          path="/CreateQuiz" 
          element={<ProtectedRoute><CreateQuiz /></ProtectedRoute>} 
        />
        <Route 
          path="/result-analysis" 
          element={<ProtectedRoute><ResultAnalysis /></ProtectedRoute>} 
        />
        <Route 
          path="/Header" 
          element={<ProtectedRoute><Header /></ProtectedRoute>} 
        />
        <Route 
          path="/Footer" 
          element={<ProtectedRoute><Footer /></ProtectedRoute>} 
        />
        <Route 
          path="/AdminLeaderboard" 
          element={<ProtectedRoute><AdminLeaderboard /></ProtectedRoute>} 
        />
        <Route 
          path="/StudentLeaderboard" 
          element={<ProtectedRoute><StudentLeaderboard /></ProtectedRoute>} 
        />
        <Route 
          path="/results" 
          element={<ProtectedRoute><ResultsPage /></ProtectedRoute>} 
        />
        <Route 
          path="/view-progress" 
          element={<ProtectedRoute><ViewProgress /></ProtectedRoute>} // Add route for ViewProgress
        />
      </Routes>
    </Router>
  );
};

export default App;