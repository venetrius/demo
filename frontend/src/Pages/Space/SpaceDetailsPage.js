import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Row, Col, Typography, Card, Statistic } from 'antd';
import { useSpaces } from '../../contexts/SpaceContext';
import { useDiscussions } from '../../contexts/SpaceDiscussionContext';

import DiscussionItem from '../Discsussion/DiscussionItem';

const { Title } = Typography;

const SpaceDetailsPage = () => {
  const { spaceId } = useParams();
  const { fetchSpace } = useSpaces();
  const { discussions, fetchDiscussions, joinDiscussion } = useDiscussions();

  let navigate = useNavigate();

  const [space, setSpace] = useState(null);

  useEffect(() => {
    loadSpace();
    fetchDiscussions(spaceId);
  }, []);

  const loadSpace = async () => {
    console.log('loadSpace', spaceId);
    const space = await fetchSpace(spaceId);
    setSpace(space);
  };

  const handleCreateDiscussion = () => {
    navigate(`/spaces/${spaceId}/new-discussion`);
  };

  return (
    <div>
      {space && (
        <>
          <Title level={2} style={{ color: 'var(--primary-color)' }}>
            {space.name}
          </Title>
          <p>{space.description}</p>
        </>
      )}
      <Title level={4}>Statistics</Title>
      <Row gutter={[16, 16]} style={{ marginBottom: 20 }}>
        <Col>
          <Card>
            <Statistic title="Number of discussions" value={discussions.length} />
          </Card>
        </Col>
        <Col>
          <Card>
            <Statistic title="Number of related arguments" value={1} />
          </Card>
        </Col>
        <Col>
          <Card>
            <Statistic title="Number of users" value={3} />
          </Card>
        </Col>
      </Row>
      <Title level={4}>Create Discussion</Title>
      <Button type="primary" onClick={handleCreateDiscussion} style={{ marginBottom: 20 }}>
        Create Discussion
      </Button>
      <Title level={4}>Discussions in this topic</Title>
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        {discussions.map((discussion) => (
          <Col key={discussion.id} span={24}>
            <DiscussionItem discussion={discussion} handleJoinDiscussion={joinDiscussion} />
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default SpaceDetailsPage;
