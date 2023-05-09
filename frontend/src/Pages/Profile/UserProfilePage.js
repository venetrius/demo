import React from 'react';
import { Descriptions, Avatar, Typography } from 'antd';
import './UserProfile.css';

const { Title } = Typography;

const UserProfilePage = () => {
    const user = {
        userName: "venetrius",
        email: "venetrius@venetrius.com",
        joinDate: "2023/03/14",
        biography: "",
        interests: "cheese",
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
      </Descriptions>
    </div>
  );
};

export default UserProfilePage;
