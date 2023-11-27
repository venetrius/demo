import React, { useEffect, useState } from 'react';
import { Button, Row, Col, Switch } from 'antd';
import { useNavigate } from "react-router-dom";
import SpaceItem from './SpaceItem';
import { useSpaces } from '../../contexts/SpaceContext'

const SpaceList = () => {
  const { spaces, fetchSpaces, userSpaces, fetchUserSpaces } = useSpaces();
  const [showUserSpaces, setShowUserSpaces] = useState(false);
  let navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      fetchSpaces();
      fetchUserSpaces();
    }, 100);
  }, []);

  const handleCreateSpace = () => {
    navigate('/new-space');
  };

  const handleShowUserSpacesChange = (checked) => {
    setShowUserSpaces(checked);
  };

  const spacesToDisplay = showUserSpaces ? userSpaces : spaces;

  console.log({userSpaces, spaces, spacesToDisplay});

  return (
    <div>
      <Button type="primary" onClick={handleCreateSpace}>
        Create Space
      </Button>
      <Switch
        checkedChildren="Joined Spaces"
        unCheckedChildren="All Spaces"
        onChange={handleShowUserSpacesChange}
        style={{ marginLeft: 20 }} />
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        {spacesToDisplay.map((space) => (
          <Col span={24} key={space.id}>
            <SpaceItem space={space} />
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default SpaceList;