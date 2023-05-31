import React, { createContext, useContext, useState, useEffect } from 'react';
import { useAuth } from './AuthContext';

const SpaceDiscussionContext = createContext();

const DiscussionProvider = ({ children }) => {
  const [discussions, setDiscussions] = useState([]);

  const { token } = useAuth();

  const createDiscussion = async (NewDiscussion) => {
    const response = await fetch('http://localhost:8080/api/discussions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(NewDiscussion),
    });

    if (response.ok) {
      const data = await response.json();
      setDiscussions(data);
      return data;
    } else {
      return false;
    }
  };

  const fetchDiscussion = async (spaceId, discussionId) => {
    const response = await fetch(`http://localhost:8080/api/discussions/${discussionId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      return data
    } else {
      console.log(`Failed to fetch discussionId with id: ${discussionId}.`);
    }
  };

  const fetchDiscussions = async (spaceId) => {
    const response = await fetch(`http://localhost:8080/api/spaces/${spaceId}/discussions`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setDiscussions(data)
    } else {
      console.log('Failed to fetch discussions.');
    }
  };

  const joinDiscussion = async (discussionId, body) => {
    const response = await fetch(`http://localhost:8080/api/me/discussions/${discussionId}/join`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(body)
    });
    if (response.ok) {
      return true;
    } else {
      return false;
    }
  };

  return (
    <SpaceDiscussionContext.Provider value={{ discussions, createDiscussion, fetchDiscussion, fetchDiscussions, joinDiscussion }}>
      {children}
    </SpaceDiscussionContext.Provider>
  );
};

const useDiscussions = () => useContext(SpaceDiscussionContext);

export { DiscussionProvider, useDiscussions };
