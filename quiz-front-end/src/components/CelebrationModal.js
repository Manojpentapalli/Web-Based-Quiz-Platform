// CelebrationModal.js
import React from 'react';
import '../styles/CelebrationModal.css';

const CelebrationModal = ({ score, totalQuestions, onClose }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>🎉 Congratulations! 🎉</h2>
        <p>You scored <strong>{score}</strong> out of <strong>{totalQuestions}</strong></p>
        <button className="close-btn" onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default CelebrationModal;
