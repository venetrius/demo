import React from 'react';
import { Card, Statistic, Row, Col, Button } from 'antd';
import { Link } from 'react-router-dom';
import { LikeOutlined, PlusCircleOutlined, MinusCircleOutlined } from '@ant-design/icons';
import { useSpaces } from '../../contexts/SpaceContext';

const SpaceItem = ({ space }) => {
  const { followSpace, likeSpace } = useSpaces();

  const onLike = async () => {
    await likeSpace(space.id);
  };

  const onJoin = async () => {
    const operation = await followSpace(space.id);
  };

  const onLeave = () => {
    // TODO Handle leave action
  };

  return (
    <Card
      title={<Link style={{color: 'white'}} to={`/spaces/${space.id}`}>{space.name}</Link>}
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
        {space.isJoined ?
          <Col>
            <Button icon={<MinusCircleOutlined />} onClick={onLeave}>
              Leave
            </Button>
          </Col>
          :
          <Col>
            <Button icon={<PlusCircleOutlined />} onClick={onJoin}>
              Join
            </Button>
          </Col>
        }
      </Row>
    </Card>
  );
};

export default SpaceItem;
