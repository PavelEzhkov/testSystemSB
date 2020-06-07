DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS exams;
DROP TABLE IF EXISTS users_exams;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS variant;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(250) NOT NULL,
password VARCHAR(250) NOT NULL,
role enum('STUDENT', 'TRAINER') NOT NULL
);

CREATE TABLE exams (
id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
name VARCHAR(250) NOT NULL);

CREATE TABLE users_exams (
users_id INT REFERENCES users(id),
exams_id INT REFERENCES exams(id));

CREATE TABLE questions (
id INT AUTO_INCREMENT  PRIMARY KEY,
exams_id INT REFERENCES exams(id),
question_text VARCHAR(2000) NOT NULL,
open_question BIT);

CREATE TABLE variants (
id INT AUTO_INCREMENT  PRIMARY KEY,
question_id INT REFERENCES questions(id),
answer_text VARCHAR(2000) NOT NULL,
correct_answer BIT);

CREATE TABLE answers (
id INT AUTO_INCREMENT  PRIMARY KEY,
question_id INT REFERENCES questions(id),
users_id INT REFERENCES users(id),
answer_text VARCHAR(2000) NOT NULL);

CREATE TABLE results (
id INT AUTO_INCREMENT  PRIMARY KEY,
exams_id INT REFERENCES exams(id),
users_id INT REFERENCES users(id),
result INT);

INSERT INTO users (name, password, role) VALUES
('student1', 'password1', 'STUDENT'),
('student2', 'password2', 'STUDENT'),
('trainer', 'password', 'TRAINER');

INSERT INTO exams (name) VALUES
('test exam');

INSERT INTO users_exams (users_id, exams_id) VALUES
('1', '1'),
('2', '1');

INSERT INTO questions (exams_id, question_text,  open_question) VALUES
('1', 'Open question',  '1'),
('1', 'Close question',  '0'),
('1', 'Close question2',  '0'),
('1', 'Close question3',  '0'),
('1', 'Close question4',  '0');

INSERT INTO variants (answer_text, question_id, correct_answer) VALUES
('answer1', '1',  '1'),
('answer1', '2',  '0'),
('answer2', '2',  '0'),
('answer3', '2',  '1'),
('answer4', '2',  '0'),
('answer1', '3',  '0'),
('answer2', '3',  '0'),
('answer3', '3',  '1'),
('answer4', '3',  '0'),
('answer1', '4',  '0'),
('answer2', '4',  '0'),
('answer3', '4',  '1'),
('answer4', '4',  '0'),
('answer1', '5',  '0'),
('answer2', '5',  '0'),
('answer3', '5',  '1'),
('answer4', '5',  '0');

INSERT INTO answers (question_id, users_id, answer_text) VALUES
('1', '1', 'answer1'),
('1', '2', 'answer2'),
('2', '1', 'answer3'),
('2', '2', 'answer2'),
('3', '1', 'answer3'),
('3', '2', 'answer1'),
('4', '1', 'answer1'),
('4', '2', 'answer3'),
('4', '1', 'answer3'),
('4', '2', 'answer4');

INSERT INTO results (exams_id, users_id, result) VALUES
('1', '1', 80),
('1', '2', 20);