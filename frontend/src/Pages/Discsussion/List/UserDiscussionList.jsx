
import React, { useEffect } from 'react';
import { Row, Col } from 'antd';
import { useUserDiscussions } from '../../../contexts/UserDiscussionContext'
import { useAuth } from '../../../contexts/AuthContext';
import DiscussionItem from '../DiscussionItem';

const UserDiscussionList = () => {
    const { authInitialized } = useAuth();
    const { subscribedDiscussions, fetchSubscribedDiscussions } = useUserDiscussions();

    useEffect(() => {
        fetchSubscribedDiscussions();
    }, [authInitialized]);

    return (
        <Row style={{ marginTop: 20 }}>
            {subscribedDiscussions.map((discussion) => (
                <Col span={22} key={discussion.id}>
                    <DiscussionItem discussion={discussion} />
                </Col>
            ))}
        </Row>
    )
}

export default UserDiscussionList;