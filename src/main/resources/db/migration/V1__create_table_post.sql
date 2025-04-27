-- Таблица с пользователями
create table if not exists users(
  id bigserial primary key,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  age integer not null,
  active boolean not null
);

insert into users(first_name, last_name, age, active) values ('Иван', 'Иванов', 30, true);
insert into users(first_name, last_name, age, active) values ('Петр', 'Петров', 25, false);
insert into users(first_name, last_name, age, active) values ('Мария', 'Сидорова', 28, true);

CREATE TABLE post (
    post_id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_path VARCHAR(255),
    content TEXT NOT NULL,
    preview_content VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO POST(POST_ID,TITLE,CONTENT,PREVIEW_CONTENT)
VALUES
('0a2a4b69-1141-4652-8c6f-ae7fae3ae6e7','test','test test','test');
