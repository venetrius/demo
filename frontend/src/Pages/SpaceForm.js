import React from 'react';
import { Form, Input, Button } from 'antd';
import { useNavigate } from "react-router-dom";
import { useSpaces } from '../contexts/SpaceContext';

const SpaceForm = () => {
  let navigate = useNavigate();
  const { createSpace } = useSpaces();

  const handleSubmit = async (values) => {
    const success = await createSpace(values);
    if (success) {
      navigate('/spaces')
    } else {
      console.log("Failed to create new Space")
    }
  };

  return (
    <Form
      layout="vertical"
      onFinish={ handleSubmit }
    >
      <Form.Item
        label="Name"
        name="name"
        rules={[
          {
            required: true,
            min: 3,
            max: 50,
            message: 'Name must be between 3 and 50 characters',
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Description"
        name="description"
        rules={[
          {
            required: true,
            min: 10,
            max: 200,
            message: 'Description must be between 10 and 200 characters',
          },
        ]}
      >
        <Input.TextArea />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Create Space
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SpaceForm;
