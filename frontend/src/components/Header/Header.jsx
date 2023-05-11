import React from 'react';
import { Layout, Menu } from 'antd';
import { NavLink, useLocation } from 'react-router-dom';
import { LogoutOutlined } from '@ant-design/icons';

import { useAuth } from '../../contexts/AuthContext';

const { Header } = Layout;

const HeaderComponent = () => {
  const { isLoggedIn, logout } = useAuth();
  const location = useLocation();
  const activeKey = location.pathname === '/' ? '1' : location.pathname.slice(1);

  const handleLogout = () => {
    logout();
  };

  return (
    <Header style={{ display: 'flex', alignItems: 'center' }}>
      <div className="logo">
        <NavLink to="/">
          <h2 style={{ color: 'white', margin: 0 }}>ArgueWise</h2>
        </NavLink>
      </div>
      <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[activeKey]} style={{ width: '100%' }}>
        <Menu.Item key="1">
          <NavLink to="/">
            Home
          </NavLink>
        </Menu.Item>
        <Menu.Item key="spaces">
          <NavLink to="/spaces">
            Spaces
          </NavLink>
        </Menu.Item>
        <Menu.Item key="discussions">
          <NavLink to="/discussions">
            Discussions
          </NavLink>
        </Menu.Item>
        <Menu.Item key="arguments">
          <NavLink to="/arguments">
            Arguments
          </NavLink>
        </Menu.Item>
        {isLoggedIn ? (
          <>
            <Menu.Item key="profile" style={{ marginLeft: 'auto' }}>
              <NavLink to="/profile">
                Profile
              </NavLink>
            </Menu.Item>
            <Menu.Item key="logout" onClick={handleLogout}>
              <LogoutOutlined />
            </Menu.Item>
          </>
        ) : (
          <Menu.Item key="login" style={{ marginLeft: 'auto' }}>
            <NavLink to="/login">
              Login/Register
            </NavLink>
          </Menu.Item>
        )}
      </Menu>
    </Header>
  );
};

export default HeaderComponent;
