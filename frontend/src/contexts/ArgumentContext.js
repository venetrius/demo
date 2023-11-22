import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'

const ArgumentContext = createContext();

const ArgumentProvider = ({ children }) => {
  const [argumentlist, setArgumentlist] = useState([]);
  const [argument, setArgument] = useState(null)
  const [suggestions, setSuggestions] = useState([]);

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

  const upvoteArgument = async (argumentId) => {
    const response = await fetch(`${getApiUrl()}/api/arguments/${argumentId}/vote`, {
      method: 'PUT',

      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ voteType: "UPVOTE" }),
    });
    if (response.ok) {
      console.log("upvoteArgument call is successful")
      return true
    } else {
      console.log('Failed to fetch arguments.');
    }
  }

  const addSuggestion = async (argumentId, newSuggestion) => {
    const response = await fetch(`${getApiUrl()}/api/arguments/${argumentId}/suggestions`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(newSuggestion),
    });
    if (response.ok) {
      console.log("addSuggestion call is successful")
      return true
    } else {
      console.log('Failed to fetch arguments.');
    }
  }

  const listSuggestions = async (argumentId) => {
    const response = await fetch(`${getApiUrl()}/api/arguments/${argumentId}/suggestions`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      }
    });
    if (response.ok) {
      setSuggestions(await response.json())
      console.log("listSuggestions call is successful")
      return true
    } else {
      console.log('Failed to list suggestions.');
    }
  }

  return (
    <ArgumentContext.Provider value={{ addSuggestion, listSuggestions, suggestions, argument, argumentlist, createArgument, fetchArgument, fetchArguments, upvoteArgument }}>
      {children}
    </ArgumentContext.Provider>
  );
};

const useArguments = () => useContext(ArgumentContext);

export { ArgumentProvider, useArguments };
