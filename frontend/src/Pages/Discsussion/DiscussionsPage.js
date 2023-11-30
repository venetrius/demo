import React, { useEffect, useState } from 'react';
import { Row, Col, Switch } from 'antd';
import DiscussionItem from './DiscussionItem';
import { useUserDiscussions } from '../../contexts/UserDiscussionContext'
import { useAuth } from '../../contexts/AuthContext';
// import InfiniteScroll from '../../components/InfiniteScroll/InfiniteScroll.jsx';
import RecommendedDiscussionList from './List/RecommendedDiscussionList';
import UserDiscussionList from './List/UserDiscussionList';

const DiscussionsPage = () => {
  const [showSubscribedDescriptions, setShowSubscribedDescriptions] = useState(false);

  const handleShowUserDiscussionsChange = (checked) => {
    setShowSubscribedDescriptions(checked);
  };

  return (
    <div>
      <Switch
        checkedChildren="Joined Discussions"
        unCheckedChildren="Discussions from your spaces"
        onChange={handleShowUserDiscussionsChange}
        style={{ marginLeft: 20 }} />
      { showSubscribedDescriptions && <UserDiscussionList />}
      { !showSubscribedDescriptions && <RecommendedDiscussionList/> }
    </div>
  );
};

export default DiscussionsPage;