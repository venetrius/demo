import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const SpaceContext = createContext();

const SpaceProvider = ({ children }) => {
  const [spaces, setSpaces] = useState([]);
  const [userSpaces, setUserSpaces] = useState([]);
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

  const fetchUserSpaces = async () => {
    const response = await fetch('http://localhost:8080/api/me/spaces', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setUserSpaces(data);
    }
  };

  const addSpace = (newSpace) => {
    setSpaces((prevSpaces) => [newSpace, ...prevSpaces]);
  };

  const followSpace = async (spaceId) => {
    const response = await fetch(`http://localhost:8080/api/me/spaces/${spaceId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    });
    if (response.ok) {
        const allSpaces = spaces.map(space => {
          if(space.id == spaceId){
            space.isJoined = true
          }
          return space
        })
        setSpaces(allSpaces)
        fetchUserSpaces();
    } else {
        console.log('Failed to follow space.');
    }
  }

  const fetchSpace = async (spaceId) => {
    const response = await fetch(`http://localhost:8080/api/spaces/${spaceId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      return(data);
    } else {
      console.log('Failed to fetch space details.');
    }
  };

  const likeSpace = async (spaceId) => {
      const response = await fetch(`http://localhost:8080/api/spaces/${spaceId}/like`, {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`
          }
      });
      if (response.ok) {
      } else {
          console.log('Failed to like space.');
      }
    }

  return (
    <SpaceContext.Provider value={{
        spaces,
        fetchSpace,
        fetchSpaces,
        followSpace,
        addSpace,
        userSpaces,
        fetchUserSpaces,
        createSpace,
        likeSpace
    }}>
      {children}
    </SpaceContext.Provider>
  );
};

const useSpaces = () => useContext(SpaceContext);

export { SpaceProvider, useSpaces };
