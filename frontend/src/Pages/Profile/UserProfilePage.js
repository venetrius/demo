import React from 'react';
import { Descriptions, Avatar, Typography, Switch } from 'antd';
import './UserProfile.css';
import { useAuth } from '../../contexts/AuthContext.js';
import { useNavigate } from 'react-router-dom';

const { Title } = Typography;

const UserProfilePage = () => {
  const { userProfile, isLoggedIn } = useAuth();
  let navigate = useNavigate();

  if(!isLoggedIn) {
    navigate('/')
  }

  if(!userProfile) return "loading"

  const user = {
      ...userProfile,
      joinDate: "2023/03/14",
      argumentsCount: 0,
      spacesCount: 0,
      discussionsCount: 0
  }

  return (
    <div className="user-profile">
      <Avatar size={100} src={user.profilePicture} />
      <Title level={2}>{user.fullName || user.userName}</Title>

      <Descriptions layout="vertical" bordered>
        <Descriptions.Item label="Username">{user.userName}</Descriptions.Item>
        <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
        <Descriptions.Item label="Join Date">{user.joinDate}</Descriptions.Item>
        <Descriptions.Item label="Biography">{user.biography}</Descriptions.Item>
        <Descriptions.Item label="Interests">{user.interests}</Descriptions.Item>
        <Descriptions.Item label="Participation">
          {`Spaces: ${user.spacesCount}, Discussions: ${user.discussionsCount}, Arguments: ${user.argumentsCount}`}
        </Descriptions.Item>
        <Descriptions.Item label="Social Media">{user.socialMediaLinks}</Descriptions.Item>
        <Descriptions.Item label="Receive Notifications">
            <Switch checked={user.receiveNotifications} onChange={() => alert("work in progress")} />
        </Descriptions.Item>
      </Descriptions>
    </div>
  );
};

export default UserProfilePage;
