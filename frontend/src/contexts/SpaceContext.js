import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const SpaceContext = createContext();

const SpaceProvider = ({ children }) => {
  const [spaces, setSpaces] = useState([]);
  const [discussions, setDiscussions] = useState([]);

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

  const createDiscussion = async (NewDiscsussion) => {
    const response = await fetch('http://localhost:8080/api/discussions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(NewDiscsussion),
    });

    if (response.ok) {
      const data = await response.json();
      addDiscussion(data);
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

  const addDiscussion = (NewDiscsussion) => {
    setDiscussions((prevDiscussions) => [NewDiscsussion, ...[prevDiscussions]]);
  };

  return (
    <SpaceContext.Provider value={{ spaces, fetchSpaces, addSpace, createSpace, createDiscussion }}>
      {children}
    </SpaceContext.Provider>
  );
};

const useSpaces = () => useContext(SpaceContext);

export { SpaceProvider, useSpaces };
