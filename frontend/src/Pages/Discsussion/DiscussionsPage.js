import React, { useEffect, useState } from 'react';
import { Row, Col, Switch } from 'antd';
import DiscussionItem from './DiscussionItem';
import { useUserDiscussions } from '../../contexts/UserDiscussionContext'
import { useAuth } from '../../contexts/AuthContext';

const DiscussionsPage = () => {
  const { authInitialized } = useAuth();
  const { subscribedDiscussions, recommendedDiscussions, fetchSubscribedDiscussions, fetchRecommendedDiscussions } = useUserDiscussions();
  const [showSubscribedDescriptions, setShowSubscribedDescriptions] = useState(false);

  useEffect(() => {
    fetchSubscribedDiscussions();
    fetchRecommendedDiscussions();
  }, [authInitialized]);

  const handleShowUserDiscussionsChange = (checked) => {
    setShowSubscribedDescriptions(checked);
  };

  const discussionsToDisplay = showSubscribedDescriptions ? subscribedDiscussions : recommendedDiscussions;

  return (
    <div>
      <Switch
        checkedChildren="Joined Discussions"
        unCheckedChildren="Discussions from your spaces"
        onChange={handleShowUserDiscussionsChange}
        style={{ marginLeft: 20 }} />
      <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
        {discussionsToDisplay.map((discussion) => (
          <Col key={discussion.id}>
            <DiscussionItem discussion={discussion} />
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default DiscussionsPage;