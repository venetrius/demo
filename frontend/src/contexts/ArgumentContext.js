import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const ArgumentContext = createContext();

const ArgumentProvider = ({ children }) => {
  const [argumentlist, setArgumentlist] = useState([]);
  const [argument, setArgument] = useState(null)

  const { token } = useAuth();

  const createArgument = async (discussionId, newArgument) => {
    // console.log({discussionId, url: `${getApiUrl()}/api/discussions/${discussionId}/arguments`})
    const response = await fetch( `${getApiUrl()}/api/discussions/${discussionId}/arguments`, {
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

  const fetchArgument = async (argumentId) => {
    const response = await fetch(`${getApiUrl()}/api/arguments/${argumentId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setArgument(data)
      return data
    } else {
      console.log(`Failed to fetch argumentId with id: ${argumentId}.`);
    }
  };

  const fetchArguments = async (discussionId) => {
    const response = await fetch(`${getApiUrl()}/api/discussions/${discussionId}/arguments`, {
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
    <ArgumentContext.Provider value={{ argument, argumentlist, createArgument, fetchArgument, fetchArguments }}>
      {children}
    </ArgumentContext.Provider>
  );
};

const useArguments = () => useContext(ArgumentContext);

export { ArgumentProvider, useArguments };
