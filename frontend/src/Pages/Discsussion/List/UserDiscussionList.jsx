
import React, { useEffect } from 'react';
import { Col } from 'antd';
import { useUserDiscussions } from '../../../contexts/UserDiscussionContext'
import { useAuth } from '../../../contexts/AuthContext';
import DiscussionItem from '../DiscussionItem';
import InfiniteScroll from '../../../components/InfiniteScroll/InfiniteScroll';

const UserDiscussionList = () => {
    const { authInitialized } = useAuth();
    const { subscribedDiscussions, fetchSubscribedDiscussions, hasMore, page } = useUserDiscussions();

    useEffect(() => {
        fetchSubscribedDiscussions();
    }, [authInitialized]);

    return (
        <InfiniteScroll 
            observableList={[subscribedDiscussions]} 
            hasMore={hasMore} 
            api={{ fetch: fetchSubscribedDiscussions, page }}
        >
            {subscribedDiscussions.map((discussion) => (
                <Col key={`subscribedDiscussions-${discussion.id}`} span={24}>
                    <DiscussionItem discussion={discussion} />
                </Col>
            ))}
        </InfiniteScroll>
    )
}

export default UserDiscussionList;