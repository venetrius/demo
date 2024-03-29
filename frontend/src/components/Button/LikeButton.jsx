import React from 'react';
import { Button } from 'antd';

const LikeButton = ({ onLike, likes, likedByUser }) => {
  const [liked, setLiked] = React.useState(likedByUser);
  
  const handleLike = () => {
    setLiked(!liked);
    if (onLike) {
      onLike(!liked);
    }
  };

  return (
    <Button type="link" onClick={handleLike}>
      {liked ? (
        <i className="fas fa-thumbs-up" style={{ color: 'blue' }} />
      ) : (
        <i className="far fa-thumbs-up" />
      )}
      &nbsp; {likes}
    </Button>
  );
};

export default LikeButton;
