import React from 'react';
import { Layout, Menu } from 'antd';
import { NavLink } from 'react-router-dom';

const { Header } = Layout;

const HeaderComponent = () => {
  return (
<Header>
  <div className="logo" />
  <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
    <Menu.Item key="1" >
      <NavLink to="/" >
        Home
      </NavLink>
    </Menu.Item>
    <Menu.Item key="2">
      <NavLink to="/spaces" >
        Spaces
      </NavLink>
    </Menu.Item>
    <Menu.Item key="3">
      <NavLink to="/profile" >
        Profile
      </NavLink>
    </Menu.Item>
  </Menu>
</Header>
  );
};

export default HeaderComponent;
