
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS poll_options;
DROP TABLE IF EXISTS poll;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS lecture;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(50) DEFAULT 'ROLE_USER'
);

CREATE TABLE course (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE lecture (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    material_url VARCHAR(255)
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    content VARCHAR(1000),
    timestamp TIMESTAMP,
    lecture_id BIGINT,
    CONSTRAINT fk_comment_lecture FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

CREATE TABLE poll (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(500)
);

CREATE TABLE poll_option (
    poll_id BIGINT,
    option_text VARCHAR(255),
    CONSTRAINT fk_poll_option FOREIGN KEY (poll_id) REFERENCES poll(id)
);

CREATE TABLE vote (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    selected_option VARCHAR(255),
    timestamp TIMESTAMP,
    poll_id BIGINT,
    CONSTRAINT fk_vote_poll FOREIGN KEY (poll_id) REFERENCES poll(id)
);
