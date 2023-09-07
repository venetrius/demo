import React, { createContext, useContext, useState, useEffect } from 'react';
import { getApiUrl } from './settings'


const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [token, setToken] = useState(null);
  const [userProfile, setUserProfile] = useState(null);

  useEffect(() => {
    const savedToken = localStorage.getItem('token');
    if (savedToken) {
      setToken(savedToken);
      setIsLoggedIn(true);
      fetchUserProfile(savedToken);
    }
  }, []);

  useEffect(() => {
    if (token) {
      fetchUserProfile(token);
    }
  }, [token]);

  const login = (token) => {
    setToken(token);
    setIsLoggedIn(true);
    localStorage.setItem('token', token);
    fetchUserProfile(token)
  };

  const logout = () => {
    setToken(null);
    setIsLoggedIn(false);
    setUserProfile(null);
    localStorage.removeItem('token');
  };

  const fetchUserProfile = async (token) => {
    const response = await fetch(`${getApiUrl()}/api/profile`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
    });

    if (response.ok) {
      const data = await response.json();
      setUserProfile(data);
    } else {
      logout();
    }
  };

  return (
    <AuthContext.Provider
      value={{
        isLoggedIn,
        token,
        userProfile,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = () => useContext(AuthContext);

export { AuthProvider, useAuth };
