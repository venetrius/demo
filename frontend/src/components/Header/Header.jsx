import React from 'react';
import { Layout, Menu } from 'antd';

const { Header } = Layout;

const HeaderComponent = () => {
  return (
    <Header>
      <div className="logo" />
      <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
        <Menu.Item key="1">
          <a href="/">Home</a>
        </Menu.Item>
        <Menu.Item key="2">
          <a href="/spaces">Spaces</a>
        </Menu.Item>
        <Menu.Item key="3">
          <a href="/profile">Profile</a>
        </Menu.Item>
      </Menu>
    </Header>
  );
};

export default HeaderComponent;
