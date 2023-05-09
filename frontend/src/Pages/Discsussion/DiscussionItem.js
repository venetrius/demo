import React from 'react';
import { List, Tag, Typography, Button, Space } from 'antd';
import moment from 'moment';

const { Text } = Typography;

const DiscussionItem = ({ discussion }) => {
  const renderActions = () => {
    return (
      <Space>
        <Button size="small" type="primary">
          Join
        </Button>
        <Button size="small">
          Leave
        </Button>
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

  return (
    <List.Item
      key={discussion.id}
      actions={[renderActions()]}
      style={{ borderBottom: '1px solid var(--border-color)', marginBottom: 20 }}
    >
      <List.Item.Meta
        title={<Text style={{ color: 'var(--primary-color)' }}>{discussion.topic}</Text>}
        description={
          <>
            <Tag color={discussion.status === 'active' ? 'green' : 'red'}>
              {discussion.status}
            </Tag>
            <Text type="secondary">
              Created by: {discussion.creatorId} |{' '}
              {moment(discussion.creationTimestamp).format('MMM DD, YYYY')} | Time limit:{' '}
              {moment(discussion.timeLimit).format('MMM DD, YYYY h[h] m[m]')}
            </Text>
            {renderStatistics()}
          </>
        }
      />
    </List.Item>
  );
};

export default DiscussionItem;