import React, { useState } from 'react';
import { Button, Col, Switch } from 'antd';
import { useNavigate } from "react-router-dom";
import SpaceItem from './SpaceItem';
import { useSpaces } from '../../contexts/SpaceContext'
import { useUserSpaces } from '../../contexts/UserSpaceContext';

import InfiniteScroll from '../../components/InfiniteScroll/InfiniteScroll.jsx';

const SpaceList = () => {

  const useSpacesObj = useSpaces();
  const useUserSpacesObj = useUserSpaces();
  const [showUserSpaces, setShowUserSpaces] = useState(false);
  let navigate = useNavigate();

  const handleCreateSpace = () => {
    navigate('/new-space');
  };

  const handleShowUserSpacesChange = (checked) => {
    setShowUserSpaces(checked);
  };

  // TODO should refactor this to use 2 separate infinite scroll components
  const spacesToDisplay = showUserSpaces ? useUserSpacesObj.userSpaces : useSpacesObj.spaces;
  const fetch = showUserSpaces ? useUserSpacesObj.fetchUserSpaces : useSpacesObj.fetchSpaces;
  const page = showUserSpaces ? useUserSpacesObj.page : useSpacesObj.page;
  const hasMore = showUserSpaces ? useUserSpacesObj.hasMore : useSpacesObj.hasMore;
  const observableList = showUserSpaces ? [useUserSpacesObj.fetchUserSpaces] : [useSpacesObj.fetchSpaces];

  return (
    <div>
      <Button type="primary" onClick={handleCreateSpace}>
        Create Space
      </Button>
      <Switch
        checkedChildren="Joined Spaces"
        unCheckedChildren="All Spaces"
        onChange={handleShowUserSpacesChange}
        style={{ marginLeft: 20 }} />
      {showUserSpaces &&
        <InfiniteScroll
          children={spacesToDisplay.map((space) => (
            <Col span={24} key={showUserSpaces + space.id}>
              <SpaceItem space={space} />
            </Col>
          ))}
          observableList={observableList}
          hasMore={hasMore}
          api={{
            fetch: fetch,
            page: page,
          }}
        >

        </InfiniteScroll>
      }
      {!showUserSpaces &&
        <InfiniteScroll
          children={spacesToDisplay.map((space) => (
            <Col span={24} key={showUserSpaces + space.id}>
              <SpaceItem space={space} />
            </Col>
          ))}
          observableList={observableList}
          hasMore={hasMore}
          api={{
            fetch: fetch,
            page: page,
          }}
        >

        </InfiniteScroll>
      }
    </div>
  );
};

export default SpaceList;