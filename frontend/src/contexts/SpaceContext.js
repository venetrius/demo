import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const SpaceContext = createContext();

const SpaceProvider = ({ children }) => {
  const [spaces, setSpaces] = useState([]);
  const { token } = useAuth();

  useEffect(() => {
    fetchSpaces();
  }, [token]);

  const createSpace = async (newSpaceData) => {
    const response = await fetch('http://localhost:8080/api/spaces', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(newSpaceData),
    });

    if (response.ok) {
      const data = await response.json();
      addSpace(data);
      return data;
    } else {
      return false;
    }
  };

  const fetchSpaces = async () => {
    if (!token) {
      setSpaces([]);
      return;
    }

    const response = await fetch('http://localhost:8080/api/spaces', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setSpaces(data);
    }
  };

  const addSpace = (newSpace) => {
    setSpaces((prevSpaces) => [newSpace, ...prevSpaces]);
  };

  return (
    <SpaceContext.Provider value={{ spaces, fetchSpaces, addSpace, createSpace }}>
      {children}
    </SpaceContext.Provider>
  );
};

const useSpaces = () => useContext(SpaceContext);

export { SpaceProvider, useSpaces };
