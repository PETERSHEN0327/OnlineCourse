
-- 插入教师账户
INSERT INTO users (id, username, password, full_name, email, phone, role)
VALUES (1, 'teacher1', '$2a$10$1LKh0hF9i1JKob13n78Zweq8Plhd0V76cKzVqP/9mM2DgxqwuBZvq', 'Mr. Lee', 'lee@example.com', '12345678', 'ROLE_TEACHER');

-- 插入学生账户
INSERT INTO users (id, username, password, full_name, email, phone, role)
VALUES (2, 'student1', '$2a$10$1LKh0hF9i1JKob13n78Zweq8Plhd0V76cKzVqP/9mM2DgxqwuBZvq', 'Student A', 'student@example.com', '87654321', 'ROLE_STUDENT');

-- 插入课程
INSERT INTO course (id, name) VALUES (1, 'COMP 3820 Online Course');

-- 插入讲座
INSERT INTO lecture (id, title, material_url) VALUES (1, 'Lecture 1: Introduction', '/uploads/lecture1.pdf');

-- 插入评论
INSERT INTO comment (id, username, content, timestamp, lecture_id)
VALUES (1, 'student1', 'Great lecture!', '2025-04-07T09:11:12.478392', 1);

-- 插入投票
INSERT INTO poll (id, question) VALUES (1, 'Which date do you prefer for the mid-term test?');

-- 插入投票选项（H2 会通过 ElementCollection 存储为子表）
INSERT INTO poll_options (poll_id, options) VALUES (1, 'Week 5');
INSERT INTO poll_options (poll_id, options) VALUES (1, 'Week 6');
INSERT INTO poll_options (poll_id, options) VALUES (1, 'Week 7');
INSERT INTO poll_options (poll_id, options) VALUES (1, 'Week 8');

-- 插入投票记录
INSERT INTO vote (id, username, selected_option, timestamp, poll_id)
VALUES (1, 'student1', 'Week 6', '2025-04-07T09:11:12.478405', 1);
