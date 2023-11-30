
import React, { useEffect } from 'react';
import { Row, Col } from 'antd';
import { useRecommendedDiscussions } from '../../../contexts/RecommendedDiscussionContext'
import { useAuth } from '../../../contexts/AuthContext';
import DiscussionItem from '../DiscussionItem';

const RecommendedDiscussionList = () => {
    const { authInitialized } = useAuth();
    const { recommendedDiscussions, fetchRecommendedDiscussions } = useRecommendedDiscussions();

    useEffect(() => {
        fetchRecommendedDiscussions();
    }, [authInitialized]);

    console.log({recommendedDiscussions})
    return (
        <Row style={{ marginTop: 20 }}>
            {recommendedDiscussions.map((discussion) => (
                <Col span={22} key={discussion.id}>
                    <DiscussionItem discussion={discussion} />
                </Col>
            ))}
        </Row>
    )
}

export default RecommendedDiscussionList;