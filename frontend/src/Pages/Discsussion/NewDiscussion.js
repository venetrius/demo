import React from 'react';
import { Navigate  } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import DiscussionForm from './DiscussionForm';

const NewDiscsussion = () => {
  const { isLoggedIn } = useAuth();

  if (!isLoggedIn) {
    return <Navigate  to="/Discussions" />;
  }

  return (
    <div>
      <h1>Create a New Discsussion</h1>
      <DiscussionForm />
    </div>
  );
};

export default NewDiscsussion;