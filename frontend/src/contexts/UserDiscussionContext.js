import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const UserDiscussionContext = createContext();

const UserDiscussionProvider = ({ children }) => {
  const [subscribedDiscussions, setSubscribedDiscussions] = useState([]);
  const [ inProgress, setInProgress ] = useState(false);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  const { token } = useAuth();

  const fetchSubscribedDiscussions = async () => {
    if(!token || !hasMore || inProgress) return;
    setInProgress(true);
    // TODO add debounce
    const prevPage = page;
    const prevContent = subscribedDiscussions;

    const response = await fetch(`${getApiUrl()}/api/me/discussions?page=${page}&size=5`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const { content, totalPages } = await response.json();
      setSubscribedDiscussions([...prevContent, ...content]);
      setPage(prevPage + 1);
      setHasMore(totalPages > page);
      setInProgress(false);
    } else {
      console.log(`Failed to fetch subscribed descriptions.`);
    }
  };

  return (
    <UserDiscussionContext.Provider value={{ page, hasMore, subscribedDiscussions, fetchSubscribedDiscussions }}>
      {children}
    </UserDiscussionContext.Provider>
  );
};

const useUserDiscussions = () => useContext(UserDiscussionContext);

export { UserDiscussionProvider, useUserDiscussions };
