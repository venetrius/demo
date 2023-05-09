import React from 'react';
import { Layout, Typography } from 'antd';
import './HomePage.css';

const { Content } = Layout;
const { Title } = Typography;

const HomePage = () => {
  return (
    <Layout>
      <Content className="home-background">
        <div className="home-content">
          <Title level={1}>Welcome to ArgueWise</Title>
          <Title level={3}>Engage in structured, respectful, and productive discussions</Title>
        </div>
      </Content>
    </Layout>
  );
};

export default HomePage;
