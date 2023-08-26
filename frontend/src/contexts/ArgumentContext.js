import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { API_URL } from './settings'

const ArgumentContext = createContext();

const ArgumentProvider = ({ children }) => {
  const [argumentlist, setArgumentlist] = useState([]);

  const { token } = useAuth();

  const createArgument = async (discussionId, newArgument) => {
    // console.log({discussionId, url: `${API_URL}/api/discussions/${discussionId}/arguments`})
    const response = await fetch( `${API_URL}/api/discussions/${discussionId}/arguments`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(newArgument),
    });

    if (response.ok) {
      const data = await response.json();
      setArgumentlist([...argumentlist, data]);
      return data;
    } else {
      return false;
    }
  };

  const fetchArgument = async (discussionId, argumentId) => {
    const response = await fetch(`${API_URL}/api/arguments/${argumentId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      return data
    } else {
      console.log(`Failed to fetch argumentId with id: ${argumentId}.`);
    }
  };

  const fetchArguments = async (discussionId) => {
    const response = await fetch(`${API_URL}/api/discussions/${discussionId}/arguments`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setArgumentlist(data)
    } else {
      console.log('Failed to fetch arguments.');
    }
  };


  return (
    <ArgumentContext.Provider value={{ argumentlist, createArgument, fetchArgument, fetchArguments }}>
      {children}
    </ArgumentContext.Provider>
  );
};

const useArguments = () => useContext(ArgumentContext);

export { ArgumentProvider, useArguments };
