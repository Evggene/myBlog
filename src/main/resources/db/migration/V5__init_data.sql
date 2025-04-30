
INSERT INTO POSTS(ID,TITLE,CONTENT,TEXT_PREVIEW, IMAGE_PATH)
VALUES
('0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7','test','test test','test', 'avatar-11060.jpeg'),
('0a2a4b69-2222-4652-8c6f-ae7fae3ae6e7','test2','test2 test2','test2', '');

INSERT INTO LIKES(post_id, likes_count)
VALUES
('0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7', 5);

INSERT INTO TAGS(id, name)
values
('0a2a4b69-6789-4652-8c6f-ae7fae3ae6e7', 'testTag'),
('0a2a4b69-2222-4652-8c6f-ae7fae3ae6e7', 'testTag2');

INSERT INTO posts_tags(post_id, tag_id)
values
('0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7', '0a2a4b69-6789-4652-8c6f-ae7fae3ae6e7'),
('0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7', '0a2a4b69-2222-4652-8c6f-ae7fae3ae6e7');

INSERT INTO comments (id, post_id , content )
values
('77c218d5-62f7-400e-b69c-c46be84150c3', '0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7', 'some comment')
