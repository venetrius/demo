import React from 'react';
import { Card } from 'antd';
import { Link } from 'react-router-dom';

const SpaceItem = ({ space }) => {
  return (
    <Link to={`/spaces/${space.id}`}>
      <Card title={space.name} style={{ width: 300, marginBottom: 20 }}>
        <p>{space.description}</p>
      </Card>
    </Link>
  );
};

export default SpaceItem;
