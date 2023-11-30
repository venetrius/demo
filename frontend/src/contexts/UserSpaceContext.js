import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'


const UserSpaceContext = createContext();

const UserSpaceProvider = ({ children }) => {
  const [userSpaces, setUserSpaces] = useState([]);
  const { token } = useAuth();
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    fetchUserSpaces();
  }, [token]);

  const fetchUserSpaces = async () => {
    if (!token || !hasMore) return;
    const response = await fetch(`${getApiUrl()}/api/me/spaces?page=${page}&size=5`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const {content, totalPages } = await response.json();
      setUserSpaces(prev => [...prev, ...content]);
      setPage(prev => prev + 1);
      setHasMore(totalPages > page);
      console.log({userSpaces})
    }
  };

  return (
    <UserSpaceContext.Provider value={{
        page,
        userSpaces,
        fetchUserSpaces,
        hasMore
    }}>
      {children}
    </UserSpaceContext.Provider>
  );
};

const useUserSpaces = () => useContext(UserSpaceContext);

export { UserSpaceProvider, useUserSpaces };
