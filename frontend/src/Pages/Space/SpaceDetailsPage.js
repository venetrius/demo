import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Row, Col, Typography } from 'antd';
import { useAuth } from '../../contexts/AuthContext';

const { Title } = Typography;

const SpaceDetailsPage = () => {
  const { spaceId } = useParams();
  const { token } = useAuth();
  let navigate = useNavigate();

  const [space, setSpace] = useState(null);
  const [discussions, setDiscussions] = useState([]);

  useEffect(() => {
    fetchSpace();
    fetchDiscussions();
  }, []);

  const fetchSpace = async () => {
    const response = await fetch(`http://localhost:8080/api/spaces/${spaceId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setSpace(data);
    } else {
      console.log('Failed to fetch space details.');
    }
  };

  const fetchDiscussions = async () => {
    const response = await fetch(`http://localhost:8080/api/spaces/${spaceId}/discussions`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      setDiscussions(data);
    } else {
      console.log('Failed to fetch discussions.');
    }
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
