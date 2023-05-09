import React, { useEffect, useState } from 'react';
import { Button, Row, Col } from 'antd';
import { useNavigate } from "react-router-dom";
import SpaceItem from './SpaceItem';
import { useSpaces } from '../../contexts/SpaceContext'

const SpaceList = () => {
  const { spaces, fetchSpaces } = useSpaces();
  let navigate = useNavigate();

  useEffect(() => {
    setTimeout(function(){
      fetchSpaces();
    }, 100);
  }, []);

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
