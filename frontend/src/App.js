import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Layout } from 'antd';
import HeaderComponent from './components/Header/Header.jsx';
import FooterComponent from './components/Footer/Footer.js';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';

import { AuthProvider } from './contexts/AuthContext';

const { Content } = Layout;

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Layout className="layout">
          <HeaderComponent />
          <Content style={{ padding: '0 50px', marginTop: 64 }}>
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Routes>
          </Content>
          <FooterComponent />
        </Layout>
      </Router>
    </AuthProvider>
    );
};

export default App;
