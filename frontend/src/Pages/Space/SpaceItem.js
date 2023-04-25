import React from 'react';
import { Card } from 'antd';

const SpaceItem = ({ space }) => {
  return (
    <Card title={space.name} style={{ width: 300, marginBottom: 20 }}>
      <p>{space.description}</p>
    </Card>
  );
};

export default SpaceItem;
