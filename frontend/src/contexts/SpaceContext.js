import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings';

const SpaceContext = createContext();

const SpaceProvider = ({ children }) => {
  const [spaces, setSpaces] = useState([]);
  const { token } = useAuth();
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    fetchSpaces();
  }, [token]);

  const createSpace = async (newSpaceData) => {
    const response = await fetch(`${getApiUrl()}/api/spaces`, {
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
    console.log('fetchSpaces', page, hasMore)
    if (!token || !hasMore) return;

    const response = await fetch(`${getApiUrl()}/api/spaces?page=${page}&size=5`, { // Adjust size as needed
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const { content, totalPages } = await response.json();
      setSpaces(prev => [...prev, ...content]); // Append new data
      setPage(prev => prev + 1);
      setHasMore(totalPages > page);
    }
  };

  const addSpace = (newSpace) => {
    setSpaces((prevSpaces) => [newSpace, ...prevSpaces]);
  };

  const followSpace = async (spaceId) => {
    const response = await fetch(`${getApiUrl()}/api/me/spaces/${spaceId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });
    if (response.ok) {
      const allSpaces = spaces.map(space => {
        if (space.id == spaceId) {
          space.isJoined = true
        }
        return space
      })
      setSpaces(allSpaces)
      // TODO should reset userSpaces
    } else {
      console.log('Failed to follow space.');
    }
  }

  const fetchSpace = async (spaceId) => {
    const response = await fetch(`${getApiUrl()}/api/spaces/${spaceId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      return (data);
    } else {
      console.log('Failed to fetch space details.');
    }
  };

  const likeSpace = async (spaceId) => {
    const response = await fetch(`${getApiUrl()}/api/spaces/${spaceId}/like`, {
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
      page,
      spaces,
      fetchSpace,
      fetchSpaces,
      followSpace,
      addSpace,
      createSpace,
      likeSpace,
      hasMore
    }}>
      {children}
    </SpaceContext.Provider>
  );
};

const useSpaces = () => useContext(SpaceContext);

export { SpaceProvider, useSpaces };
