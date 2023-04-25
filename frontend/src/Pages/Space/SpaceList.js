import React, { useEffect, useState } from 'react';
import { Button, Row, Col } from 'antd';
import { useNavigate } from "react-router-dom";
import { useAuth } from '../../contexts/AuthContext';
import SpaceItem from './SpaceItem';

const SpaceList = () => {
  const [spaces, setSpaces] = useState([]);
  const { token, isLoggedIn } = useAuth();
  let navigate = useNavigate();

  useEffect(() => {
    setTimeout(function(){
      fetchSpaces();
    }, 1000);
  }, []);

  const fetchSpaces = async () => {
      console.log({token, isLoggedIn})

    if(!token) {
      return;
    }
    const response = await fetch('http://localhost:8080/api/spaces', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setSpaces(data);
    } else {
        console.log('Failed to fetch spaces.');
    }
  };

  const handleCreateSpace = () => {
    navigate('/new-space');
  };

  return (
    <div>
      <Button type="primary" onClick={handleCreateSpace}>
        Create Space
      </Button>
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        {spaces.map((space) => (
          <Col key={space.id}>
            <SpaceItem space={space} />
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default SpaceList;
