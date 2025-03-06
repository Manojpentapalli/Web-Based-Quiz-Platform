import React from "react";

const TestTable = () => {
  const tests = [
    { id: 1, name: "Introduction to Java", questions: "10 questions", marks: "50 marks", duration: "30 mins" },
    { id: 2, name: "Advance Java", questions: "20 questions", marks: "100 marks", duration: "60 mins" },
    { id: 3, name: "Structured Query Language", questions: "15 questions", marks: "75 marks", duration: "45 mins" },
    { id: 4, name: ".NET Core", questions: "25 questions", marks: "125 marks", duration: "90 mins" },
    { id: 5, name: "C++ Programming", questions: "30 questions", marks: "150 marks", duration: "120 mins" },
  ];

  return (
    <div className="table-container">
      <h2>Available Tests</h2>
      <table className="test-table">
        <thead>
          <tr>
            <th>Test Name</th>
            <th>Questions</th>
            <th>Marks</th>
            <th>Duration</th>
          </tr>
        </thead>
        <tbody>
          {tests.map((test) => (
            <tr key={test.id}>
              <td>{test.name}</td>
              <td>{test.questions}</td>
              <td>{test.marks}</td>
              <td>{test.duration}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TestTable;
