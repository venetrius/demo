ALTER TABLE arguments ADD COLUMN side VARCHAR(255);

UPDATE arguments
SET side = (
    SELECT ud.side
    FROM users_discussions ud
    WHERE ud.discussion_id = arguments.discussion_id
      AND ud.user_id = arguments.author_id
);

ALTER TABLE arguments ALTER COLUMN side SET NOT NULL;
