
import React, { useEffect } from 'react';
import { Row, Col } from 'antd';
import { useRecommendedDiscussions } from '../../../contexts/RecommendedDiscussionContext'
import { useAuth } from '../../../contexts/AuthContext';
import DiscussionItem from '../DiscussionItem';
import InfiniteScroll from '../../../components/InfiniteScroll/InfiniteScroll';

const RecommendedDiscussionList = () => {
    const { authInitialized } = useAuth();
    const { recommendedDiscussions, fetchRecommendedDiscussions, hasMore, page } = useRecommendedDiscussions();

    useEffect(() => {
        fetchRecommendedDiscussions();
    }, [authInitialized]);

    console.log({recommendedDiscussions})
    return (
        <InfiniteScroll
            observableList={[recommendedDiscussions]}
            hasMore={hasMore}
            api={{ fetch: fetchRecommendedDiscussions, page }}
        >
            {recommendedDiscussions.map((discussion) => (
                <Col span={22} key={discussion.id}>
                    <DiscussionItem discussion={discussion} />
                </Col>
            ))}
        </InfiniteScroll>
    )
}

export default RecommendedDiscussionList;