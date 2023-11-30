import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Layout } from 'antd';

import HeaderComponent from './components/Header/Header.jsx';
import FooterComponent from './components/Footer/Footer.js';
import Login from './components/Auth/Login';
import HomePage from './Pages/Home/HomePage.js'

import Register from './components/Auth/Register';
// TODO typo
import DiscussionDetails from './Pages/Discsussion/DiscussionDetails.js';
import DiscussionsPage from './Pages/Discsussion/DiscussionsPage.js'
import NewDiscussion from './Pages/Discsussion/NewDiscussion.js'
import NewSpace from './Pages/Space/NewSpace';
import UserProfilePage from './Pages/Profile/UserProfilePage.js'
import SpaceList from './Pages/Space/SpaceList';
import SpaceDetailsPage from './Pages/Space/SpaceDetailsPage.js'
import ArgumentDetails from './Pages/Argument/ArgumentDetails.jsx'

import { AuthProvider } from './contexts/AuthContext';
import { SpaceProvider } from './contexts/SpaceContext';
import { UserSpaceProvider } from './contexts/UserSpaceContext';
import { DiscussionProvider } from './contexts/SpaceDiscussionContext';
import { UserDiscussionProvider } from './contexts/UserDiscussionContext.js'
import { RecommendedDiscussionProvider } from './contexts/RecommendedDiscussionContext.js';
import { ArgumentProvider } from './contexts/ArgumentContext';

import './App.css';

const { Content } = Layout;

const App = () => {
  return (
    <AuthProvider>
      <SpaceProvider>
        <UserSpaceProvider>
          <DiscussionProvider>
            <UserDiscussionProvider>
              <RecommendedDiscussionProvider>
                <ArgumentProvider>
                  <Router>
                    <Layout className="layout">
                      <HeaderComponent />
                      <Content style={{ padding: '50px', minHeight: 'calc(100vh - 114px)' }}>
                        <Routes>
                          <Route path="/" element={<HomePage />} />
                          <Route path='/discussions/:discussionId/arguments/:argumentId' element={<ArgumentDetails />} />
                          <Route path='/discussions' element={<DiscussionsPage />} />
                          <Route path="/login" element={<Login />} />
                          <Route path="/register" element={<Register />} />
                          <Route path="/spaces/:spaceId" element={<SpaceDetailsPage />} />
                          <Route path="spaces/:spaceId/new-discussion" element={<NewDiscussion />} />
                          <Route path="spaces/:spaceId/discussions/:discussionId" element={<DiscussionDetails />} />
                          <Route path="/spaces" element={<SpaceList />} />
                          <Route path="/new-space" element={<NewSpace />} />
                          <Route path="/profile" element={<UserProfilePage />} />
                        </Routes>
                      </Content>
                      <FooterComponent />
                    </Layout>
                  </Router>
                </ArgumentProvider>
              </RecommendedDiscussionProvider>
            </UserDiscussionProvider>
          </DiscussionProvider>
        </UserSpaceProvider>
      </SpaceProvider>
    </AuthProvider>
  );
};

export default App;
