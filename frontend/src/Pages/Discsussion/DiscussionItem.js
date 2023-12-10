import React from 'react';
import { Card, List, Tag, Typography, Button, Space } from 'antd';
import { Link } from 'react-router-dom';
import moment from 'moment';

const { Text } = Typography;

const DiscussionItem = ({ discussion, handleJoinDiscussion }) => {  
  const shouldRenderJoinButtons = (!discussion.currentUsersSide && discussion.status === 'ACTIVE')

  const handleOnJoinClick = (side) => {
    handleJoinDiscussion(discussion.id, { side })
  };

  const renderActions = () => {
    return (
      <Space>
        { shouldRenderJoinButtons &&
          <>
            <Button size="small" type="primary" onClick={() => handleOnJoinClick("PRO")}>
              Join as PRO
            </Button>
            <Button size="small" type="primary" onClick={() => handleOnJoinClick("CONTRA")}>
              Join as CONTRA
            </Button>
          </>}
        <Button size="small" type="dashed">
          Like
        </Button>
      </Space>
    );
  };

  const renderStatistics = () => {
    return (
      <Space>
        <Text strong>Participants: {discussion.participantsCount || 0}</Text>
        <Text strong>Arguments: {discussion.argumentsCount || 0}</Text>
        <Text strong>Likes: {discussion.likesCount || 0}</Text>
      </Space>
    );
  };

  const renderTimeStamps = () => {
    return (
      <Text type="secondary" style={{ display: 'block', margin: '8px 0' }}>
        Created by: {discussion.creatorName} |{' '}
        {moment(discussion.creationTimestamp).format('MMM DD, YYYY')} | Time limit:{' '}
        {moment(discussion.timeLimit).format('MMM DD, YYYY h[h] m[m]')}
      </Text>
    );
  };

  return (
    <Card
      key={discussion.id}
      actions={[renderActions()]}
      style={{ borderBottom: '1px solid var(--border-color)', marginBottom: 10 }}
    >
      <List.Item.Meta
        title={
          <>
            <Link to={`/spaces/${discussion.spaceID}/discussions/${discussion.id}`}>
              <Text style={{ color: 'var(--primary-color)', fontSize: '16px', fontWeight: 'bold' }}>{discussion.topic}</Text>
            </Link>
            <Tag style={{ marginLeft: '5px' }} color={discussion.status === 'active' ? 'green' : 'red'}>
              {discussion.status}
            </Tag>
            {discussion.currentUsersSide && (<Tag color={discussion.currentUsersSide === 'PRO' ? 'green' : 'red'}>{discussion.currentUsersSide}</Tag>)}
          </>
        }
        description={
          <>
            <div>
              {renderStatistics()}
            </div>
            {renderTimeStamps()}
          </>
        }
      />
    </Card>
  );
};

export default DiscussionItem;