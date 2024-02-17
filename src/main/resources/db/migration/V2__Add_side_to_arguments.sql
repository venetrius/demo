ALTER TABLE arguments ADD COLUMN side VARCHAR(255);

UPDATE arguments
SET side = (
    SELECT ud.side
    FROM users_discussions ud
    WHERE ud.discussion_id = arguments.discussion_id
      AND ud.user_id = arguments.author_id
);

UPDATE arguments
SET side = 'PRO'
WHERE side is null;

ALTER TABLE arguments ALTER COLUMN side SET NOT NULL;
