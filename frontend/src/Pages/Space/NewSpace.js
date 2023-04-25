import React from 'react';
import { Navigate  } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import SpaceForm from './SpaceForm';

const NewSpace = () => {
  const { isLoggedIn } = useAuth();

  if (!isLoggedIn) {
    return <Navigate  to="/spaces" />;
  }

  return (
    <div>
      <h1>Create a New Space</h1>
      <SpaceForm />
    </div>
  );
};

export default NewSpace;
