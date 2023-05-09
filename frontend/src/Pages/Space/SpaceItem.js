import React from 'react';
import { Card, Statistic, Row, Col, Button } from 'antd';
import { Link } from 'react-router-dom';
import { LikeOutlined, PlusCircleOutlined, MinusCircleOutlined } from '@ant-design/icons';

const SpaceItem = ({ space }) => {
  const onLike = () => {
    // TODO Handle like action
  };

  const onJoin = () => {
    // TODO Handle join action
  };

  const onLeave = () => {
    // TODO Handle leave action
  };

  return (
    <Card
      title={space.name}
      style={{ width: 300, marginBottom: 20 }}
      headStyle={{ backgroundColor: '#1890ff', color: 'white' }}
    >
      <Link to={`/spaces/${space.id}`}>
        <p>{space.description}</p>
        <Row gutter={[16, 16]}>
          <Col>
            <Statistic title="Members" value={space.numberOfPeopleJoined} />
          </Col>
          <Col>
            <Statistic title="Discussions" value={space.numberOfDiscussions} />
          </Col>
        </Row>
      </Link>
      <Row gutter={[16, 16]} style={{ marginTop: 16 }}>
        <Col>
          <Button icon={<LikeOutlined />} onClick={onLike}>
            Like
          </Button>
        </Col>
        <Col>
          <Button icon={<PlusCircleOutlined />} onClick={onJoin}>
            Join
          </Button>
        </Col>
        <Col>
          <Button icon={<MinusCircleOutlined />} onClick={onLeave}>
            Leave
          </Button>
        </Col>
      </Row>
    </Card>
  );
};

export default SpaceItem;
