import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const UserDiscussionContext = createContext();

const UserDiscussionProvider = ({ children }) => {
  const [subscribedDiscussions, setSubscribedDiscussions] = useState([]);

  const { token } = useAuth();

  const fetchSubscribedDiscussions = async () => {
    const response = await fetch(`${getApiUrl()}/api/me/discussions`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setSubscribedDiscussions(data.content)
      return data
    } else {
      console.log(`Failed to fetch subscribed descriptions.`);
    }
  };

  return (
    <UserDiscussionContext.Provider value={{ subscribedDiscussions, fetchSubscribedDiscussions }}>
      {children}
    </UserDiscussionContext.Provider>
  );
};

const useUserDiscussions = () => useContext(UserDiscussionContext);

export { UserDiscussionProvider, useUserDiscussions };
