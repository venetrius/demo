import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'


const UserDiscussionContext = createContext();

const UserDiscussionProvider = ({ children }) => {
  const [subscribedDiscussions, setSubscirbedDiscussions] = useState([]);
  const [recommendedDiscussions, setRecommendedDiscussions] = useState([]);


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
      setSubscirbedDiscussions(data)
      return data
    } else {
      console.log(`Failed to fetch subscribed descriptions.`);
    }
  };

  const fetchRecommendedDiscussions = async () => {
    const response = await fetch(`${getApiUrl()}/api/me/discussions/recommendations`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setRecommendedDiscussions(data)
    } else {
      console.log('Failed to fetch discussions.');
    }
  };

  return (
    <UserDiscussionContext.Provider value={{ subscribedDiscussions, recommendedDiscussions, fetchSubscribedDiscussions, fetchRecommendedDiscussions }}>
      {children}
    </UserDiscussionContext.Provider>
  );
};

const useUserDiscussions = () => useContext(UserDiscussionContext);

export { UserDiscussionProvider, useUserDiscussions };
