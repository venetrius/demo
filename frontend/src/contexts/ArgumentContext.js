import React, { createContext, useContext, useState } from 'react';
import { useAuth } from './AuthContext';
import { getApiUrl } from './settings'
import requestHandler from './request';

const ArgumentContext = createContext();

const ArgumentProvider = ({ children }) => {
  const [argumentlist, setArgumentlist] = useState([]);
  const [argument, setArgument] = useState(null)
  const [suggestions, setSuggestions] = useState([]);

  const { token } = useAuth();

  const { put } = requestHandler(token);

  const createArgument = async (discussionId, newArgument) => {
    // console.log({discussionId, url: `${getApiUrl()}/api/discussions/${discussionId}/arguments`})
    const response = await fetch(`${getApiUrl()}/api/discussions/${discussionId}/arguments`, {
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

  const voteOnSuggestion = async (argumentId, suggestionId, voteType) => {
    const body = JSON.stringify({ voteType })
    const url = `${getApiUrl()}/api/arguments/${argumentId}/suggestions/${suggestionId}/vote`
    const response = await put(url, body);
    if (response.ok) {
      console.log("upvoteSuggestion call is successful")
      const suggestionsClone = [...suggestions]
      const suggestionIndex = suggestionsClone.findIndex(suggestion => suggestion.id === suggestionId)
      suggestionsClone[suggestionIndex].votes += voteType === "UPVOTE" ? 1 : -1
      suggestionsClone[suggestionIndex].likedByCurrentUser = true
      setSuggestions(suggestionsClone)
      return true
    } else {
      console.log('Failed to send upvote to suggestion.');
    }
  }

  const deleteSuggestionVote = async (argumentId, suggestionId) => {
    const response = await fetch(`${getApiUrl()}/api/arguments/${argumentId}/suggestions/${suggestionId}/vote`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });
    if (response.ok) {
      console.log("deleteSuggestionVote call is successful")
      const suggestionsClone = [...suggestions]
      const suggestionIndex = suggestionsClone.findIndex(suggestion => suggestion.id === suggestionId)
      suggestionsClone[suggestionIndex].votes -= 1
      suggestionsClone[suggestionIndex].likedByCurrentUser = false
      setSuggestions(suggestionsClone)
      return true
    } else {
      console.log('Failed to delete upvote from suggestion.');
    }
  }

  return (
    <ArgumentContext.Provider 
      value={{ 
        addSuggestion,
        deleteSuggestionVote,
        listSuggestions,
        suggestions, 
        argument, 
        argumentlist, 
        createArgument, 
        fetchArgument, 
        fetchArguments, 
        upvoteArgument, 
        voteOnSuggestion 
      }}>
      {children}
    </ArgumentContext.Provider>
  );
};

const useArguments = () => useContext(ArgumentContext);

export { ArgumentProvider, useArguments };
