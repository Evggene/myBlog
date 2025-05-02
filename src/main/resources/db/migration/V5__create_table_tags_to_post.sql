
CREATE TABLE tags_to_post (
    post_id UUID NOT NULL,
    tag_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT uk_tags_to_post_post_tag UNIQUE (post_id, tag_id)
);

CREATE INDEX idx_tags_to_post_tag_id ON tags_to_post (tag_id);
