import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Row, Col, Typography } from 'antd';
import { useSpaces } from '../../contexts/SpaceContext'
import { useDiscussions } from '../../contexts/DiscussionContext'

const { Title } = Typography;

const SpaceDetailsPage = () => {
  const { spaceId } = useParams();
  const { fetchSpace } = useSpaces();
  const { discussions, fetchDiscussions } = useDiscussions();

  let navigate = useNavigate();

  const [space, setSpace] = useState(null);

  useEffect(() => {
    loadSpace();
    fetchDiscussions(spaceId);
  }, []);

  const loadSpace = async () => {
    console.log('loadSpace', spaceId)
    const space = await fetchSpace(spaceId)
    setSpace(space)
  };

  const handleCreateDiscussion = () => {
    navigate(`/spaces/${spaceId}/new-discussion`);
  };

  return (
    <div>
      {space && (
        <>
          <Title level={2}>{space.name}</Title>
          <p>{space.description}</p>
        </>
      )}
      <Button type="primary" onClick={handleCreateDiscussion}>
        Create Discussion
      </Button>
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        {discussions.map((discussion) => (
          <Col key={discussion.id}>
            {JSON.stringify(discussion)}
            {/* <DiscussionItem discussion={discussion} /> */}
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default SpaceDetailsPage;
