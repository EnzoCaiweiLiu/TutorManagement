-- CREATE SCHEMA tutor_management;

USE tutor_management;

DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Profile;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Tutor;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Rate;
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS Course;

-- User table
CREATE TABLE User (
    uid INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile BIGINT NOT NULL,
    password VARCHAR(255) NOT NULL,
    type INT NOT NULL
);

INSERT INTO User (uid, name, email, mobile, password, type) VALUES
(1, 'Admin Name', 'admin@ad.unsw.edu.au', 0123456789, 'adminpass', 1),
(2, 'Student1 Name', 'student1@ad.unsw.edu.au', 1234567890, 'student1pass', 2),
(3, 'Tutor1 Name', 'tutor1@ad.unsw.edu.au', 2345678901, 'tutor1pass', 3),
(4, 'Student2 Name', 'student2@ad.unsw.edu.au', 3456789012, 'student2pass', 2),
(5, 'Tutor2 Name', 'tutor2@ad.unsw.edu.au', 4567890123, 'tutor2pass', 3);

-- Profile table
CREATE TABLE Profile (
    uid INT PRIMARY KEY,
    address VARCHAR(255),
    time_zone DATETIME,
    bio_url VARCHAR(255),
    bio TEXT,
    summary TEXT,
    document VARCHAR(255),
    FOREIGN KEY (uid) REFERENCES User(uid) ON DELETE CASCADE
);

INSERT INTO Profile (uid, address, time_zone, bio_url, bio, summary, document) VALUES
(1, 'Admin Address', '2023-10-01 10:00:00', 'admin_bio_url', 'Admin Bio', 'Admin Summary', 'admin_document.pdf'),
(2, 'Student1 Address', '2023-10-01 11:00:00', 'student1_bio_url', 'Student1 Bio', 'Student1 Summary', 'student1_document.pdf'),
(3, 'Tutor1 Address', '2023-10-01 12:00:00', 'tutor1_bio_url', 'Tutor1 Bio', 'Tutor1 Summary', 'tutor1_document.pdf'),
(4, 'Student2 Address', '2023-10-01 13:00:00', 'student2_bio_url', 'Student2 Bio', 'Student2 Summary', 'student2_document.pdf'),
(5, 'Tutor2 Address', '2023-10-01 14:00:00', 'tutor2_bio_url', 'Tutor2 Bio', 'Tutor2 Summary', 'tutor2_document.pdf');

-- Admin table
CREATE TABLE Admin (
    uid INT PRIMARY KEY,
    FOREIGN KEY (uid) REFERENCES User(uid) ON DELETE CASCADE
);

INSERT INTO Admin (uid) VALUES (1);

-- Student table
CREATE TABLE Student (
    uid INT PRIMARY KEY,
    FOREIGN KEY (uid) REFERENCES User(uid) ON DELETE CASCADE
);

INSERT INTO Student (uid) VALUES (2), (4);

-- Tutor table
CREATE TABLE Tutor (
    uid INT PRIMARY KEY,
    FOREIGN KEY (uid) REFERENCES User(uid) ON DELETE CASCADE
);

INSERT INTO Tutor (uid) VALUES (3), (5);

-- Message table
CREATE TABLE Message (
    t_uid INT,
    s_uid INT,
    message VARCHAR(255) NOT NULL,
    PRIMARY KEY (t_uid, s_uid),
    FOREIGN KEY (t_uid) REFERENCES Tutor(uid) ON DELETE CASCADE,
    FOREIGN KEY (s_uid) REFERENCES Student(uid) ON DELETE CASCADE
);

INSERT INTO Message (t_uid, s_uid, message) VALUES
(3, 2, 'Hello Student1, this is Tutor1. How can I assist you?'),
(3, 4, 'Hello Student2, this is Tutor1. Do you have any questions?'),
(5, 2, 'Hi Student1, this is Tutor2. Let\'s schedule our next session.'),
(5, 4, 'Hi Student2, this is Tutor2. Please submit your assignment.');

-- Course table
CREATE TABLE Course (
    t_uid INT,
    cid INT,
    type INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    valid_time DATETIME NOT NULL,
    location VARCHAR(255) NOT NULL,
    PRIMARY KEY (t_uid, cid),
    FOREIGN KEY (t_uid) REFERENCES Tutor(uid) ON DELETE CASCADE
);

INSERT INTO Course (t_uid, cid, type, name, valid_time, location) VALUES
(3, 101, 1, 'Mathematics 101', '2023-10-10 10:00:00', 'Room A1'),
(3, 102, 1, 'Physics 101', '2023-10-11 11:00:00', 'Room B2'),
(5, 103, 2, 'Chemistry 101', '2023-10-12 12:00:00', 'Room C3'),
(5, 104, 2, 'Biology 101', '2023-10-13 13:00:00', 'Room D4');

-- Appointment table
CREATE TABLE Appointment (
    s_uid INT,
    t_uid INT,
    cid INT,
    start_time DATETIME NOT NULL,
    finish_time DATETIME NOT NULL,
    PRIMARY KEY (s_uid, t_uid, cid),
    FOREIGN KEY (s_uid) REFERENCES Student(uid) ON DELETE CASCADE,
    FOREIGN KEY (t_uid, cid) REFERENCES Course(t_uid, cid) ON DELETE CASCADE
);

INSERT INTO Appointment (s_uid, t_uid, cid, start_time, finish_time) VALUES
(2, 3, 101, '2023-10-10 10:00:00', '2023-10-10 11:00:00'),
(2, 3, 102, '2023-10-11 11:00:00', '2023-10-11 12:00:00'),
(4, 5, 103, '2023-10-12 12:00:00', '2023-10-12 13:00:00'),
(4, 5, 104, '2023-10-13 13:00:00', '2023-10-13 14:00:00');

-- Rate table
CREATE TABLE Rate (
    t_uid INT,
    cid INT,
    s_uid INT,
    rate INT NOT NULL CHECK (rate >= 1 AND rate <= 5), -- Assuming a rating scale of 1 to 5
    PRIMARY KEY (t_uid, cid, s_uid),
    FOREIGN KEY (t_uid, cid, s_uid) REFERENCES Appointment(t_uid, cid, s_uid) ON DELETE CASCADE
);

INSERT INTO Rate (t_uid, cid, s_uid, rate) VALUES
(3, 101, 2, 5),
(3, 102, 2, 4),
(5, 103, 4, 3),
(5, 104, 4, 4);