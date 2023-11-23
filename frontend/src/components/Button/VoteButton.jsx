import React from 'react';
import { Button } from 'antd';

const VoteButton = ({ onVote, votes, userVote, onUnvote }) => {
  // userVote can be -1 (downvote), 0 (no vote), or 1 (upvote)
  const [vote, setVote] = React.useState(userVote);

  const handleVote = (newVote) => {
    console.log({ newVote, vote })
    // If the user clicks the same button again, it will remove their vote
    const updatedVote = newVote === vote ? 0 : newVote;

    setVote(updatedVote);
    if (updatedVote !== 0) {
        onVote(updatedVote);
    } else {
        onUnvote();
    }
  };

  return (
    <div>
      <Button type="link" onClick={() => handleVote(1)}>
        {vote === 1 ? (
          <i className="fas fa-thumbs-up" style={{ color: 'blue' }} />
        ) : (
          <i className="far fa-thumbs-up" />
        )}
      </Button>
      &nbsp; {votes} &nbsp;
      <Button type="link" onClick={() => handleVote(-1)}>
        {vote === -1 ? (
          <i className="fas fa-thumbs-down" style={{ color: 'red' }} />
        ) : (
          <i className="far fa-thumbs-down" />
        )}
      </Button>
    </div>
  );
};

export default VoteButton;
