import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const RecommendedDiscussionContext = createContext();

const RecommendedDiscussionProvider = ({ children }) => {
  const [recommendedDiscussions, setRecommendedDiscussions] = useState([]);
  const [ inProgress, setInProgress ] = useState(false);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  const { token } = useAuth();


  const fetchRecommendedDiscussions = async () => {
    if(!token || !hasMore || inProgress) return;

    setInProgress(true);
    // TODO add debounce
    const prevPage = page;
    const prevContent = recommendedDiscussions;

    const response = await fetch(`${getApiUrl()}/api/me/discussions/recommendations?page=${page}&size=5`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const { content, totalPages } = await response.json();
      setRecommendedDiscussions([...prevContent, ...content]);
      setPage(prevPage + 1);
      console.log('totalPages', totalPages, 'page', page, totalPages > page)
      setHasMore(totalPages > page);
      setInProgress(false);
    } else {
      console.log('Failed to fetch discussions.');
    }
  };

  return (
    <RecommendedDiscussionContext.Provider value={{ recommendedDiscussions, fetchRecommendedDiscussions, page, hasMore }}>
      {children}
    </RecommendedDiscussionContext.Provider>
  );
};

const useRecommendedDiscussions = () => useContext(RecommendedDiscussionContext);

export { RecommendedDiscussionProvider, useRecommendedDiscussions };
