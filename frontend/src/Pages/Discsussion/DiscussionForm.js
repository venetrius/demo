import React from 'react';
import { Form, Input, Button, DatePicker } from 'antd';
import { useParams, useNavigate } from 'react-router-dom';

import { useSpaces } from '../../contexts/SpaceContext';
import { useDiscussions } from '../../contexts/DiscussionContext';

const DiscussionForm = () => {
  const { spaceId } = useParams();
  let navigate = useNavigate();
  const { createDiscussion } = useDiscussions();
          

  const handleSubmit = async (values) => {
    values.spaceID = spaceId;
    const success = await createDiscussion(values);
    if (success) {
      navigate(`/spaces/${spaceId}`);
    } else {
      console.log('Failed to create new Discussion');
    }
  };

  return (
    <Form layout="vertical" onFinish={handleSubmit}>
      <Form.Item
        label="Topic"
        name="topic"
        rules={[
          {
            required: true,
            max: 100,
            message: 'Topic should be no longer than 100 characters',
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Time Limit"
        name="timeLimit"
        rules={[
          {
            required: true,
            message: 'Time limit is required',
          },
        ]}
      >
        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Create Discussion
        </Button>
      </Form.Item>
    </Form>
  );
};

export default DiscussionForm;
