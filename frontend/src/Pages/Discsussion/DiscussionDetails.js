import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Card, Radio, List, Typography, Space, Statistic, Row, Col } from 'antd';

import { useDiscussions } from '../../contexts/SpaceDiscussionContext';
import { useArguments } from '../../contexts/ArgumentContext';
import ArgumentForm from '../Argument/ArgumentForm'


const { Title, Text } = Typography;

const DiscussionDetails = () => {
  const { spaceId, discussionId } = useParams();
  const [discussion, setDiscussion] = useState(null);
  const [side, setSide] = useState(null);
  const { joinDiscussion, fetchDiscussion } = useDiscussions();
  const { argumentlist, createArgument, fetchArguments } = useArguments();

  useEffect(() => {
    loadDiscussion();
    fetchArguments();
  }, [discussionId]);

  const loadDiscussion = async () => {
    const discussionRes = await fetchDiscussion(spaceId, discussionId);
    setDiscussion(discussionRes);
  };
  console.log({discussion})
  console.log({argumentlist})

  const handleJoin = async (e) => {
    const res = await joinDiscussion(discussionId, { side: e.target.value })
    if (res) {
      discussion.currentUsersSide = e.target.value
      setDiscussion(structuredClone(discussion))
    }
  };

  if (!discussion) {
    return <div>Loading...</div>;
  }

  if (discussion && discussion.currentUsersSide && discussion.currentUsersSide != side) {
    setSide(discussion.currentUsersSide)
  }

  return (
    <div>
      <Title level={1}>{discussion.topic}</Title>
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
          <Text strong>Creator:</Text> {discussion.creatorName}
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
            <Title level={2}>Arguments</Title>
            <List
              dataSource={argumentlist}
              renderItem={(item) => (
                <List.Item>
                  <Space>
                    <Text mark>{item.title}</Text> {item.argument}
                  </Space>
                </List.Item>
              )}
            />
          </>
        )}
      </Space>
      {side &&
        <>
          <Title level={4}> Add a new Argument</Title>
          <ArgumentForm addArgument={(newArgument) => createArgument(discussionId, newArgument)} />
        </>
      }
    </div>
  );
};

export default DiscussionDetails;