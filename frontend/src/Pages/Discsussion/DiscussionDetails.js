import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Radio, List, Typography, Space, Statistic, Row, Col } from 'antd';

import { useDiscussions } from '../../contexts/DiscussionContext';
import ArgumentForm from '../Argument/ArgumentForm'


const { Title, Text } = Typography;

const DiscussionDetails = () => {
  const { spaceId, discussionId } = useParams();
  const [discussion, setDiscussion] = useState(null);
  const [side, setSide] = useState(null);
  const [argumentsList, setArgumentsList] = useState([]); // TODO fetch this data
  const { joinDiscussion, fetchDiscussion } = useDiscussions();

  useEffect(() => {
    loadDiscussion();
  }, [discussionId]);

  const loadDiscussion = async () => {
    const discussionRes = await fetchDiscussion(spaceId, discussionId);
    setDiscussion(discussionRes);
  };

  const handleJoin = async (e) => {
    const res = await joinDiscussion(discussionId, {side: e.target.value})
    if(res){
      discussion.currentUsersSide = e.target.value
      setDiscussion(structuredClone(discussion))
    }
  };

  if (!discussion) {
    return <div>Loading...</div>;
  }

  if(discussion && discussion.currentUsersSide && discussion.currentUsersSide != side) {
    setSide(discussion.currentUsersSide)
  }

  return (
    <div>
      <Title level={2}>{discussion.topic}</Title>
      <Title level={4}>Status: {discussion.status}</Title>
      <Row gutter={16}>
        <Col>
          <Statistic title="Participants" value={discussion.participantsCount} />
        </Col>
        <Col>
          <Statistic title="Arguments" value={discussion.argumentsCount} />
        </Col>
        <Col>
          <Statistic title="Likes" value={discussion.likesCount} />
        </Col>
      </Row>
      <Space direction="vertical" size="large">
        <Card>
          <Text strong>Creator ID:</Text> {discussion.creatorId}
          <br />
          <Text strong>Created at:</Text> {discussion.creationTimestamp}
          <br />
          <Text strong>Time Limit:</Text> {discussion.timeLimit}
        </Card>
        {discussion.status === 'ACTIVE' && !side && (
          <Radio.Group onChange={handleJoin}>
            <Radio.Button value="PRO">Join as PRO</Radio.Button>
            <Radio.Button value="CONTRA">Join as CONTRA</Radio.Button>
          </Radio.Group>
        )}
        {side && <Title level={4}>Your Side: {discussion.currentUsersSide}</Title>}
        {(side || discussion.status === 'COMPLETED') && (
          <>
            <Title level={3}>Arguments</Title>
            <List
              dataSource={argumentsList}
              renderItem={(item) => (
                <List.Item>
                  <Space>
                    <Text mark>{item.side}</Text> {item.argument}
                  </Space>
                </List.Item>
              )}
            />
          </>
        )}
      </Space>
      <ArgumentForm addArgument={(argument) => setArgumentsList([...argumentsList, argument])} /> {/*TODO handle create*/}
    </div>
  );
};

export default DiscussionDetails;