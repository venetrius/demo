import React from 'react';
import { Layout } from 'antd';
import HeaderComponent from './components/Header/Header.jsx';
import FooterComponent from './components/Footer/Footer.js';
import Login from './components/Auth/Login';
import { AuthProvider, useAuth } from './contexts/AuthContext';

const { Content } = Layout;

const App = () => {
  return (
    <AuthProvider>
      <Layout className="layout">
        <HeaderComponent />
        <Content style={{ padding: '0 50px', marginTop: 64 }}>
          <Login />
        </Content>
        <FooterComponent />
      </Layout>
    </AuthProvider>
  );
};

export default App;
