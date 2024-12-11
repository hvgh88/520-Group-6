// import React, { createContext, useContext, useState } from 'react';

// // Create Context
// const UserContext = createContext();

// // Custom Hook to use the User Context
// export const useUser = () => useContext(UserContext);

// // UserProvider component to wrap the app and provide the user context
// export const UserProvider = ({ children }) => {
//     const [userEmail, setUserEmail] = useState(null); // Email state to store the email of the logged-in user

//     return (
//         <UserContext.Provider value={{ userEmail, setUserEmail }}>
//             {children}
//         </UserContext.Provider>
//     );
// };

import React, { createContext, useContext, useState, useEffect } from 'react';

// Create Context
const UserContext = createContext();

// Custom Hook to use the User Context
export const useUser = () => useContext(UserContext);

// UserProvider component to wrap the app and provide the user context
export const UserProvider = ({ children }) => {
  // Initialize the state with data from localStorage if available
  const [userEmail, setUserEmail] = useState(() => {
    return localStorage.getItem("userEmail") || null;
  });

  const [userId, setUserId] = useState(() => {
    return localStorage.getItem("userId") || null;
  });

  // Update localStorage whenever userEmail or userId changes
  useEffect(() => {
    if (userEmail) {
      localStorage.setItem("userEmail", userEmail);
    }
  }, [userEmail]);

  useEffect(() => {
    if (userId) {
      localStorage.setItem("userId", userId);
    }
  }, [userId]);

  return (
    <UserContext.Provider value={{ userEmail, setUserEmail, userId, setUserId }}>
      {children}
    </UserContext.Provider>
  );
};

