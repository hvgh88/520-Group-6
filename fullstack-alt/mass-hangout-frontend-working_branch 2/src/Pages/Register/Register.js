import React, { useState } from "react";
import "./Register.css";
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; 
import config from '../../config/config';

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [isRegistered, setIsRegistered] = useState(false); 
  const navigate = useNavigate();

  // Email validation function
  const validateUMassEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@umass.edu$/;
    return emailRegex.test(email);
  };

  // Email validation function
  const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  };

  // Handle email validation on blur
  const handleEmailBlur = () => {
    if (!validateEmail(email)) {
      setEmailError('Invalid email format.');
    } else if (!validateUMassEmail(email)) {
      setEmailError('Please enter valid UMass email id')
    } else{
      setEmailError('');
    }
  };

 // Handle email input change
 const handleEmailChange = (e) => {
  const newEmail = e.target.value;
  setEmail(newEmail);
  if (emailError && validateEmail(newEmail) && validateUMassEmail(newEmail)) {
    setEmailError('');
  }
};


  // Password validation function
  const validatePassword = (password) => {
    const passwordRegex = /^[A-Za-z](?=.*\d)(?=.*[@#$!%^*?&])[A-Za-z\d!@#$%^&*?]{8,}$/;
    return passwordRegex.test(password);
  };

   // Handle password validation on blur
   const handlePasswordBlur = () => {
    if (!validatePassword(password)) {
      setPasswordError(
        'Password must be at least 8 characters long, contain letters, numbers, and at least one allowed special character (!@#$%^&*?)).'
      );
    } else {
      setPasswordError('');
    }
  };

  // Handle password input change
  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);
    if (passwordError && validatePassword(newPassword)) {
      setPasswordError('');
    }
  };

  const validateForm = () => {
    // const validationErrors = {};
    handleEmailBlur();
    handlePasswordBlur();
    if (emailError || passwordError) {
      return false; // Stop submission if there are errors
    }
    return true
  };

  const handleRegister = async (e) => {
    console.log("Inside handling registration")
    e.preventDefault();
    console.log("After prevent default")
    if (!validateForm()) {
      return
    }
    try {  
      console.log("Base URL:", config.USER_SERVICE);

      const response = await axios.post(`${config.USER_SERVICE}/register`, {
        email: email,
        password: password
      });
      console.log("response  " + response)
      console.log("response  " + response.status)
      console.log("data " + response.data.code)
      console.log("data " + response.data.message)
        // Check if the response is successful
      if (response.status === 201) {
        // Resgisteration successful, show message and then direct to login via login button
        if (response.data.code === 10001) {
          setSuccessMessage('Registration Successful. Please login');
          setErrorMessage('');
          setIsRegistered(true); 
        } else {
          // Unexpected success response (optional handling)
          setErrorMessage('Unexpected error. Please try again.');
        }
      }
    } catch (error) {
      // Handle errors based on the status code
      if (error.response) {
        const { code, message } = error.response.data;

        switch (error.response.status) {
          case 400:
            // User already exists
            if (code === 10002) {
              setErrorMessage(message);
            } else {
              // Unexpected success response (optional handling)
              setErrorMessage('Unexpected error. Please try again.');
            }
            break;

          default:
            // General error handling for unexpected errors
            setErrorMessage('Something went wrong. Please try again later.');
        }
      } else {
        // Handle errors that aren't related to response (network issues, etc.)
        setErrorMessage('Network error. Please check your connection.');
      }
    }
  };
    // if (!email.includes("@")) {
    //   validationErrors.email = "Please enter a valid email address.";
    // }
    // if (password.length < 8) {
    //   validationErrors.password = "Password must be at least 8 characters.";
    // }
    // if (!firstName) {
    //   validationErrors.firstName = "First name is required.";
    // }
    // if (!lastName) {
    //   validationErrors.lastName = "Last name is required.";
    // }
    // if (!department) {
    //   validationErrors.department = "Department is required.";
    // }
    // if (!graduationYear || graduationYear < 2020 || graduationYear > 2100) {
    //   validationErrors.graduationYear = "Enter a valid graduation year.";
    // }
    // return validationErrors;
  // };

  // const handleRegister = (e) => {
  //   e.preventDefault();
  //   if (!validateForm()) {
  //     return
  //   }
  //   // if (Object.keys(validationErrors).length > 0) {
  //   //   setErrors(validationErrors);
  //   // } else {
  //   //   // Submit data (mocked)
  //   //   console.log({
  //   //     email,
  //   //     password,
  //   //     firstName,
  //   //     middleName,
  //   //     lastName,
  //   //     department,
  //   //     graduationYear,
  //   //     bio,
  //   //   });
  //     alert("Registration successful!");
  //   //}
  // };

  return (
    <div className="register-container">
      <h2>Create Your Account</h2>
      {successMessage && <div className="success-message">{successMessage}</div>}
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      <form onSubmit={handleRegister}>
        <div className="form-group">
          <div className="field">
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={handleEmailChange}
              onBlur={handleEmailBlur}
              placeholder="Enter your UMass email"
              required
              disabled={isRegistered}
            /> 
            {emailError && <div className="error-message">{emailError}</div>}
            {/* onChange={(e) => setEmail(e.target.value)}
             placeholder="Enter your UMass email"
             
             {errors.email && <div className="error-message">{errors.email}</div>} */}
          </div>
          <div className="field">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={handlePasswordChange}
              onBlur={handlePasswordBlur}
              //onChange={(e) => setPassword(e.target.value)}
              placeholder="Create a strong password"
              required
              disabled={isRegistered}
            />
            {/* {errors.password && (
              <div className="error-message">{errors.password}</div>
            )} */}
            {passwordError && <div className="error-message">{passwordError}</div>}
          </div>
        </div>

        {/* <div className="form-group">
          <div className="field">
            <label>First Name</label>
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              placeholder="First name"
            />
            {errors.firstName && (
              <div className="error-message">{errors.firstName}</div>
            )}
          </div>
          <div className="field">
            <label>Middle Name</label>
            <input
              type="text"
              value={middleName}
              onChange={(e) => setMiddleName(e.target.value)}
              placeholder="Middle name (optional)"
            />
          </div>
          <div className="field">
            <label>Last Name</label>
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              placeholder="Last name"
            />
            {errors.lastName && (
              <div className="error-message">{errors.lastName}</div>
            )}
          </div>
        </div>

        <div className="form-group">
          <div className="field">
            <label>Department</label>
            <input
              type="text"
              value={department}
              onChange={(e) => setDepartment(e.target.value)}
              placeholder="Department"
            />
            {errors.department && (
              <div className="error-message">{errors.department}</div>
            )}
          </div>
          <div className="field">
            <label>Graduation Year</label>
            <input
              type="number"
              value={graduationYear}
              onChange={(e) => setGraduationYear(e.target.value)}
              placeholder="Graduation year"
            />
            {errors.graduationYear && (
              <div className="error-message">{errors.graduationYear}</div>
            )}
          </div>
        </div>

        <div className="field">
          <label>Bio</label>
          <textarea
            value={bio}
            onChange={(e) => setBio(e.target.value)}
            placeholder="Tell us something about yourself"
          ></textarea>
        </div> */}

        <button type="submit" className="btn-register" disabled={isRegistered}>
          Register
        </button>
      </form>
      <p>
        Already have an account? <a href="/">Sign in here!</a>
      </p>
    </div>
  );
};

export default Register;
