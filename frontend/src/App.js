import React from 'react';
import { Layout } from 'antd';
import HeaderComponent from './components/Header/Header.jsx';
import FooterComponent from './components/Footer/Footer.js';

const { Content } = Layout;

const App = () => {
  return (
    <Layout className="layout">
      <HeaderComponent />
      <Content style={{ padding: '0 50px', marginTop: 64 }}>
      </Content>
      <FooterComponent />
    </Layout>
  );
};

export default App;
