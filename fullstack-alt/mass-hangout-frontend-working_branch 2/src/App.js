import React from "react";
import { BrowserRouter as Router, Route, Routes, Navigate, useLocation } from "react-router-dom";
import Sidebar from "./components/Sidebar"; // Sidebar Component
import Login from "./Pages/Login/Login"; // Login Page
import Register from "./Pages/Register/Register"; // Register Page
import ChatPage from "./Pages/Chat/ChatPage"; // Chat Page
import Groups from "./Pages/Groups/Groups"; // Chat Page
import "./App.css"; // General styles for layout
import Profile from "./Pages/Profile/Profile";
import { UserProvider, useUser } from './UserContext'; // Import UserProvider

const Dashboard = () => {
  const { userEmail } = useUser();
  console.log("Email inside dashboard " + userEmail)
  return (
    <div style={{ padding: "20px" }}>
      {/* <h1>Dashboard</h1> */}
      {/* <p>Welcome to the Dashboard! Use the sidebar to navigate to different pages.</p> */}
    </div>
  );
};

// Main App Component
const App = () => {
  const location = useLocation(); // Get current route

  // Determine if the sidebar should be hidden
  const hideSidebar = location.pathname === "/login" || location.pathname === "/register";

  return (
    <div className="app">
      {/* Render Sidebar conditionally */}
      {!hideSidebar && <Sidebar />}

      {/* Main content area */}
      <div className={`main-content ${hideSidebar ? "center-content" : ""}`}>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} /> {/* Default Route */}
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/chat" element={<ChatPage />} />
          <Route path="/groups" element={<Groups />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </div>
    </div>
  );
};

// Wrap App with Router to use `useLocation`
const WrappedApp = () => (
  <Router>
    <UserProvider> {/* Wrap the app with the UserProvider */}
      <App />
    </UserProvider>
  </Router>
);

export default WrappedApp;
