import React, { useEffect, useRef } from 'react';
import { Row } from 'antd';

const InfiniteScroll = ({ children, observableList, hasMore, api }) => {
    const { fetch, page } = api;
    const loaderRef = useRef(null);

    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            const firstEntry = entries[0];
            if (firstEntry.isIntersecting) {
                if (!hasMore) return;
                fetch(page);
            }
        }, { threshold: 1.0 });

        const currentLoaderRef = loaderRef.current;
        if (currentLoaderRef) {
            observer.observe(currentLoaderRef);
        }

        return () => {
            if (currentLoaderRef) {
                observer.unobserve(currentLoaderRef);
            }
        };
    }, [...observableList, loaderRef]);

    return (
        <Row gutter={[16, 16]} style={{ marginTop: 20 }}>
            {children}
            <div ref={loaderRef} style={{ textAlign: 'center', marginTop: '20px' }}>
                {hasMore ? 'Loading...' : 'No more data to load'}
            </div>
        </Row>
    )
}

export default InfiniteScroll;