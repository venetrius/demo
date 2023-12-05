import React from 'react';
import { Form, Input, Button, Card } from 'antd';
import { UserOutlined, LockOutlined, MailOutlined } from '@ant-design/icons';
import { useAuth } from '../../contexts/AuthContext';
import { Link, useNavigate } from 'react-router-dom';
import { getApiUrl } from '../../contexts/settings'
import { hashPassword } from '../../util/hash.js';

const Register = () => {
  const { login } = useAuth();
  let navigate = useNavigate();

  const onFinish = async (values) => {
    const { password, email } = values
    const hash = await hashPassword(password + email)

    const response = await fetch(`${getApiUrl()}/api/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password: hash }),
    });

    if (response.ok) {
      const data = await response.json();
      login(data.token);
      navigate('/');
    } else {
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Card title="Register" style={{ width: 300 }}>
        <Form
          name="normal_register"
          className="register-form"
          initialValues={{
            remember: true,
          }}
          onFinish={onFinish}
        >
          <Form.Item
            name="email"
            rules={[
              {
                required: true,
                message: 'Please input your Email!',
              },
              {
                type: 'email',
                message: 'Please enter a valid email!',
              },
            ]}
          >
            <Input prefix={<MailOutlined className="site-form-item-icon" />} placeholder="Email" />
          </Form.Item>
          <Form.Item
            name="userName"
            rules={[
              {
                required: true,
                message: 'Please input your Username!',
              },
            ]}
          >
            <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: 'Please input your Password!',
              },
            ]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="Password"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" className="register-form-button" style={{ width: '100%' }}>
              Register
            </Button>
            Or <Link to="/">log in</Link>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Register;
