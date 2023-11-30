import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const RecommendedDiscussionContext = createContext();

const RecommendedDiscussionProvider = ({ children }) => {
  const [recommendedDiscussions, setRecommendedDiscussions] = useState([]);

  const { token } = useAuth();


  const fetchRecommendedDiscussions = async () => {
    const response = await fetch(`${getApiUrl()}/api/me/discussions/recommendations`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setRecommendedDiscussions(data.content)
    } else {
      console.log('Failed to fetch discussions.');
    }
  };

  return (
    <RecommendedDiscussionContext.Provider value={{ recommendedDiscussions, fetchRecommendedDiscussions }}>
      {children}
    </RecommendedDiscussionContext.Provider>
  );
};

const useRecommendedDiscussions = () => useContext(RecommendedDiscussionContext);

export { RecommendedDiscussionProvider, useRecommendedDiscussions };
