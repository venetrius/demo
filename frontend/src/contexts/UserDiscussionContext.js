import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const UserDiscussionContext = createContext();

const UserDiscussionProvider = ({ children }) => {
  const [subscirbedDiscussions, setSubscirbedDiscussions] = useState([]);
  const [discussionsFromSubscirbedSpaces, setdiscussionsFromSubscirbedSpaces] = useState([]);


  const { token } = useAuth();


  const fetchSubscribedDiscussions = async () => {
    const response = await fetch(`http://localhost:8080/api/me/discussions/`, {
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
    const response = await fetch(`http://localhost:8080/api/me/discussions/recommendations`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setdiscussionsFromSubscirbedSpaces(data)
    } else {
      console.log('Failed to fetch discussions.');
    }
  };

  return (
    <UserDiscussionContext.Provider value={{ subscirbedDiscussions, discussionsFromSubscirbedSpaces, fetchSubscribedDiscussions, fetchRecommendedDiscussions }}>
      {children}
    </UserDiscussionContext.Provider>
  );
};

const useUserDiscussions = () => useContext(UserDiscussionContext);

export { UserDiscussionProvider, useUserDiscussions };
