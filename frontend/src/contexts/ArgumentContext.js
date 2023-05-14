import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';

const ArgumentContext = createContext();

const ArgumentProvider = ({ children }) => {
  const [argumentlist, setArgumentlist] = useState([]);

  const { token } = useAuth();

  const createArgument = async (discussionId, newArgument) => {
    console.log({discussionId, url: `http://localhost:8080/api/discussions/${discussionId}/arguments`})
    const response = await fetch(`http://localhost:8080/api/discussions/${discussionId}/arguments`, {
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
    const response = await fetch(`http://localhost:8080/api/arguments/${argumentId}`, {
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
    const response = await fetch(`http://localhost:8080/api/discussions/${discussionId}/arguments`, {
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
