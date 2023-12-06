-- CREATE SCHEMA tutor_management;

-- USE tutor_management;

SET SQL_SAFE_UPDATES = 0;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Rate;
DROP TABLE IF EXISTS Appointment;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Tutor;
DROP TABLE IF EXISTS CourseType;
DROP TABLE IF EXISTS Verify;
DROP TABLE IF EXISTS Support;


CREATE TABLE Admin (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
		middle_name VARCHAR(50),
		last_name VARCHAR(50),
		gender VARCHAR(50),
		dob DATE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
		is_delete TINYINT(1) DEFAULT 0
);

CREATE TABLE Student (
    uid INT AUTO_INCREMENT PRIMARY KEY,
		first_name VARCHAR(50),
		middle_name VARCHAR(50),
		last_name VARCHAR(50),
		gender VARCHAR(50),
		dob DATE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(50) NOT NULL,
    mobile BIGINT,
    photo_url VARCHAR(1024),
    address VARCHAR(512),
    time_zone VARCHAR(50),
    bio TEXT,
    hide VARCHAR(255),
		is_delete TINYINT(1) DEFAULT 0
);

CREATE TABLE Tutor (
    uid INT AUTO_INCREMENT PRIMARY KEY,
		first_name VARCHAR(50),
		middle_name VARCHAR(50),
		last_name VARCHAR(50),
		gender VARCHAR(50),
		dob DATE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(50) NOT NULL,
    mobile BIGINT,
    photo_url VARCHAR(1024),
    address VARCHAR(512),
    time_zone VARCHAR(50),
    bio TEXT,
    summary TEXT,
    document VARCHAR(1024),
    hide VARCHAR(255),
		is_delete TINYINT(1) DEFAULT 0
);

CREATE TABLE Message (
    mid INT AUTO_INCREMENT PRIMARY KEY,
    tid INT,
    sid INT,
    time DATETIME NOT NULL,
		t_to_s TINYINT(1) DEFAULT 0,
    message TEXT,
		is_delete TINYINT(1) DEFAULT 0,
    FOREIGN KEY (tid) REFERENCES Tutor(uid) ON DELETE CASCADE,
    FOREIGN KEY (sid) REFERENCES Student(uid) ON DELETE CASCADE
);

CREATE TABLE CourseType (
    cid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
		is_delete TINYINT(1) DEFAULT 0
);

CREATE TABLE Course (
    csid INT AUTO_INCREMENT PRIMARY KEY,
    cid INT,
    tid INT,
    start_time DATETIME NOT NULL,
    finish_time DATETIME NOT NULL,
    location VARCHAR(255),
		is_select TINYINT(1) DEFAULT 0,
		is_delete TINYINT(1) DEFAULT 0,
    FOREIGN KEY (cid) REFERENCES CourseType(cid) ON DELETE CASCADE,
    FOREIGN KEY (tid) REFERENCES Tutor(uid) ON DELETE CASCADE
);

CREATE TABLE Appointment (
    aid INT AUTO_INCREMENT PRIMARY KEY,
    csid INT,
    tid INT,
    sid INT,
    start_time DATETIME NOT NULL,
    finish_time DATETIME NOT NULL,
		approve TINYINT(1) DEFAULT 0,
		is_delete TINYINT(1) DEFAULT 0,
    FOREIGN KEY (csid) REFERENCES Course(csid) ON DELETE CASCADE,
    FOREIGN KEY (tid) REFERENCES Tutor(uid) ON DELETE CASCADE,
    FOREIGN KEY (sid) REFERENCES Student(uid) ON DELETE CASCADE
);

CREATE TABLE Rate (
    aid INT,
    rate INT CHECK (rate >= 0 AND rate <= 5) DEFAULT 5,
		feedback VARCHAR(255),
		is_delete TINYINT(1) DEFAULT 0,
    FOREIGN KEY (aid) REFERENCES Appointment(aid) ON DELETE CASCADE
);

CREATE TABLE Verify(
		vid INT AUTO_INCREMENT PRIMARY KEY,
		email VARCHAR(255),
		code INT,
		time DATETIME
);

CREATE TABLE Support(
    sid INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    info TEXT NOT NULL,
    answer TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO Admin (first_name, middle_name, last_name, gender, dob, email, password, is_delete) VALUES
('John', 'Michael', 'Doe', 'Male', '1985-06-15', 'john.doe@email.com', 'password123!', 0),
('Jane', 'Amanda', 'Smith', 'Female', '1990-02-20', 'jane.smith@email.com', 'securePass!', 0),
('Robert', 'James', 'Johnson', 'Male', '1988-10-10', 'robert.johnson@email.com', 'robPass99', 0),
('Emily', 'Grace', 'Davis', 'Female', '1992-05-30', 'emily.davis@email.com', 'emilyPass22', 0),
('William', NULL, 'Brown', 'Male', '1987-08-15', 'william.brown@email.com', 'willPassword!', 0);

INSERT INTO Student (first_name, middle_name, last_name, gender, dob, email, password, mobile, photo_url, address, time_zone, bio, hide, is_delete) VALUES 
('Alice', 'Marie', 'Johnson', 'Female', '2000-01-05', 'alice.johnson@email.com', 'alicePassword!', 1234567890, 'http://example.com/alice.jpg', '1234 Elm Street', '2023-10-19 12:00:00', 'I love reading books.', NULL, 0),
('Bob', NULL, 'Smith', 'Male', '2001-02-15', 'bob.smith@email.com', 'bobPassword!', 1234567891, 'http://example.com/bob.jpg', '5678 Maple Drive', '2023-10-19 13:00:00', 'Football enthusiast.', 'email', 0),
('Carol', 'Lynn', 'Davis', 'Female', '2000-03-25', 'carol.davis@email.com', 'carolPassword!', 1234567892, 'http://example.com/carol.jpg', '91011 Oak Lane', '2023-10-19 14:00:00', 'Nature lover.', NULL, 0),
('David', 'George', 'Miller', 'Male', '1999-04-30', 'david.miller@email.com', 'davidPassword!', 1234567893, 'http://example.com/david.jpg', '1213 Birch Circle', '2023-10-19 15:00:00', 'Aspiring chef.', 'mobile', 0),
('Ella', NULL, 'Wilson', 'Female', '2002-05-10', 'ella.wilson@email.com', 'ellaPassword!', 1234567894, 'http://example.com/ella.jpg', '1415 Pine Court', '2023-10-19 16:00:00', 'Traveler.', NULL, 0),
('Frank', 'Drew', 'Moore', 'Male', '2001-06-20', 'frank.moore@email.com', 'frankPassword!', 1234567895, 'http://example.com/frank.jpg', '1617 Cedar Blvd', '2023-10-19 17:00:00', 'Musician at heart.', 'bio', 0),
('Grace', 'Elaine', 'Taylor', 'Female', '2000-07-15', 'grace.taylor@email.com', 'gracePassword!', 1234567896, 'http://example.com/grace.jpg', '1819 Spruce Avenue', '2023-10-19 18:00:00', 'Dancer.', NULL, 0),
('Harry', NULL, 'Anderson', 'Male', '1998-08-05', 'harry.anderson@email.com', 'harryPassword!', 1234567897, 'http://example.com/harry.jpg', '2021 Fir Place', '2023-10-19 19:00:00', 'Future pilot.', 'photo_url', 0),
('Ivy', 'Faith', 'Thomas', 'Female', '2002-09-10', 'ivy.thomas@email.com', 'ivyPassword!', 1234567898, 'http://example.com/ivy.jpg', '2324 Elmwood Park', '2023-10-19 20:00:00', 'Food blogger.', NULL, 0),
('Jack', 'Henry', 'Jackson', 'Male', '1999-10-20', 'jack.jackson@email.com', 'jackPassword!', 1234567899, 'http://example.com/jack.jpg', '2526 Willow Drive', '2023-10-19 21:00:00', 'Guitarist.', 'address', 0),
('Kate', 'Lily', 'White', 'Female', '2001-11-15', 'kate.white@email.com', 'katePassword!', 1234567810, 'http://example.com/kate.jpg', '2728 Maplewood Lane', '2023-10-19 22:00:00', 'Animal lover.', NULL, 0),
('Leo', 'James', 'Harris', 'Male', '2000-12-05', 'leo.harris@email.com', 'leoPassword!', 1234567811, 'http://example.com/leo.jpg', '2930 Oakwood Terrace', '2023-10-19 23:00:00', 'Basketball player.', 'time_zone', 0),
('Mia', NULL, 'Martin', 'Female', '2003-01-10', 'mia.martin@email.com', 'miaPassword!', 1234567812, 'http://example.com/mia.jpg', '3132 Birchwood Street', '2023-10-20 00:00:00', 'Painter.', NULL, 0),
('Nathan', 'Oliver', 'Thompson', 'Male', '1998-02-20', 'nathan.thompson@email.com', 'nathanPassword!', 1234567813, 'http://example.com/nathan.jpg', '3334 Pinecrest Road', '2023-10-20 01:00:00', 'Nature photographer.', 'first_name', 0),
('Olivia', 'Rose', 'Garcia', 'Female', '2002-03-05', 'olivia.garcia@email.com', 'oliviaPassword!', 1234567814, 'http://example.com/olivia.jpg', '3536 Cedarwood Ave', '2023-10-20 02:00:00', 'Bookworm.', NULL, 0),
('Paul', 'Samuel', 'Martinez', 'Male', '1999-04-15', 'paul.martinez@email.com', 'paulPassword!', 1234567815, 'http://example.com/paul.jpg', '3738 Sprucewood St', '2023-10-20 03:00:00', 'Love hiking.', 'last_name', 0),
('Quinn', 'Tara', 'Robinson', 'Female', '2001-05-05', 'quinn.robinson@email.com', 'quinnPassword!', 1234567816, 'http://example.com/quinn.jpg', '3940 Elmwood Court', '2023-10-20 04:00:00', 'Fitness enthusiast.', NULL, 0),
('Ryan', NULL, 'Clark', 'Male', '2000-06-10', 'ryan.clark@email.com', 'ryanPassword!', 1234567817, 'http://example.com/ryan.jpg', '4142 Maplecrest Drive', '2023-10-20 05:00:00', 'Love cooking.', 'middle_name', 0),
('Sophia', 'Uma', 'Rodriguez', 'Female', '2003-07-15', 'sophia.rodriguez@email.com', 'sophiaPassword!', 1234567818, 'http://example.com/sophia.jpg', '4344 Oakcrest Lane', '2023-10-20 06:00:00', 'Chess player.', NULL, 0),
('Tom', 'Victor', 'Lee', 'Male', '1998-08-05', 'tom.lee@email.com', 'tomPassword!', 1234567819, 'http://example.com/tom.jpg', '4546 Birchcrest Terrace', '2023-10-20 07:00:00', 'Gamer.', 'dob', 0);


INSERT INTO Tutor (first_name, middle_name, last_name, gender, dob, email, password, mobile, photo_url, address, time_zone, bio, summary, document, hide, is_delete) VALUES 
('Alan', 'Robert', 'Mitchell', 'Male', '1980-01-10', 'alan.mitchell@email.com', 'alanPassword!', 2345678901, 'http://example.com/alan.jpg', '1234 Oak Street', '2023-10-19 12:00:00', 'Mathematics PhD.', 'Experienced tutor in algebra and calculus.', 'http://example.com/alan_document.pdf', NULL, 0),
('Barbara', NULL, 'Nelson', 'Female', '1982-02-20', 'barbara.nelson@email.com', 'barbaraPassword!', 2345678902, 'http://example.com/barbara.jpg', '5678 Pine Drive', '2023-10-19 13:00:00', 'Literature Master.', 'Specializes in Shakespeare.', 'http://example.com/barbara_document.pdf', 'email', 0),
('Carl', 'Edward', 'Olson', 'Male', '1985-03-15', 'carl.olson@email.com', 'carlPassword!', 2345678903, 'http://example.com/carl.jpg', '91011 Birch Lane', '2023-10-19 14:00:00', 'Physics expert.', 'Has taught in three universities.', 'http://example.com/carl_document.pdf', NULL, 0),
('Deborah', 'Faith', 'Perez', 'Female', '1987-04-10', 'deborah.perez@email.com', 'deborahPassword!', 2345678904, 'http://example.com/deborah.jpg', '1213 Maple Circle', '2023-10-19 15:00:00', 'History major.', '10 years of tutoring experience.', 'http://example.com/deborah_document.pdf', 'mobile', 0),
('Eugene', 'George', 'Quinn', 'Male', '1975-05-05', 'eugene.quinn@email.com', 'eugenePassword!', 2345678905, 'http://example.com/eugene.jpg', '1415 Elm Court', '2023-10-19 16:00:00', 'Biology professor.', 'Researcher in marine biology.', 'http://example.com/eugene_document.pdf', NULL, 0),
('Frances', 'Helen', 'Russell', 'Female', '1983-06-30', 'frances.russell@email.com', 'francesPassword!', 2345678906, 'http://example.com/frances.jpg', '1617 Cedar Blvd', '2023-10-19 17:00:00', 'Chemistry tutor.', 'Worked in two pharmaceutical companies.', 'http://example.com/frances_document.pdf', 'bio', 0),
('Gary', NULL, 'Stewart', 'Male', '1981-07-15', 'gary.stewart@email.com', 'garyPassword!', 2345678907, 'http://example.com/gary.jpg', '1819 Spruce Avenue', '2023-10-19 18:00:00', 'Economics major.', 'Worked in Wall Street for 5 years.', 'http://example.com/gary_document.pdf', NULL, 0),
('Heather', 'Irene', 'Turner', 'Female', '1984-08-10', 'heather.turner@email.com', 'heatherPassword!', 2345678908, 'http://example.com/heather.jpg', '2021 Fir Place', '2023-10-19 19:00:00', 'Computer Science tutor.', 'Developed three mobile applications.', 'http://example.com/heather_document.pdf', 'photo_url', 0),
('Ivan', 'James', 'Upton', 'Male', '1978-09-25', 'ivan.upton@email.com', 'ivanPassword!', 2345678909, 'http://example.com/ivan.jpg', '2324 Maplewood Park', '2023-10-19 20:00:00', 'Mechanical engineer.', 'Worked in automotive industry.', 'http://example.com/ivan_document.pdf', NULL, 0),
('Jennifer', 'Kate', 'Vance', 'Female', '1986-10-30', 'jennifer.vance@email.com', 'jenniferPassword!', 2345678910, 'http://example.com/jennifer.jpg', '2526 Willow Drive', '2023-10-19 21:00:00', 'Art and Design tutor.', 'Freelance graphic designer.', 'http://example.com/jennifer_document.pdf', 'address', 0),
('Kenneth', 'Lloyd', 'Watson', 'Male', '1979-11-15', 'kenneth.watson@email.com', 'kennethPassword!', 2345678911, 'http://example.com/kenneth.jpg', '2728 Oakwood Lane', '2023-10-19 22:00:00', 'Music instructor.', 'Played in a band for 10 years.', 'http://example.com/kenneth_document.pdf', NULL, 0),
('Linda', 'Monica', 'Xavier', 'Female', '1985-12-20', 'linda.xavier@email.com', 'lindaPassword!', 2345678912, 'http://example.com/linda.jpg', '2930 Fir Terrace', '2023-10-19 23:00:00', 'Psychology major.', 'Counseled students in a school.', 'http://example.com/linda_document.pdf', 'time_zone', 0),
('Michael', 'Nathan', 'Young', 'Male', '1977-01-10', 'michael.young@email.com', 'michaelPassword!', 2345678913, 'http://example.com/michael.jpg', '3132 Pine Street', '2023-10-20 00:00:00', 'Physical education tutor.', 'Former national level athlete.', 'http://example.com/michael_document.pdf', NULL, 0),
('Nancy', 'Olive', 'Zimmerman', 'Female', '1982-02-25', 'nancy.zimmerman@email.com', 'nancyPassword!', 2345678914, 'http://example.com/nancy.jpg', '3334 Elmwood Park', '2023-10-20 01:00:00', 'Sociology professor.', 'Written two books on sociology.', 'http://example.com/nancy_document.pdf', 'first_name', 0),
('Oscar', 'Peter', 'Adams', 'Male', '1980-03-15', 'oscar.adams@email.com', 'oscarPassword!', 2345678915, 'http://example.com/oscar.jpg', '3536 Cedarwood Ave', '2023-10-20 02:00:00', 'Philosophy tutor.', 'Debated in international forums.', 'http://example.com/oscar_document.pdf', NULL, 0),
('Patricia', 'Quinn', 'Bryant', 'Female', '1983-04-10', 'patricia.bryant@email.com', 'patriciaPassword!', 2345678916, 'http://example.com/patricia.jpg', '3738 Birchwood Lane', '2023-10-20 03:00:00', 'Botany major.', 'Worked in a botanical garden.', 'http://example.com/patricia_document.pdf', 'dob', 0),
('Quincy', 'Ronald', 'Collins', 'Male', '1981-05-25', 'quincy.collins@email.com', 'quincyPassword!', 2345678917, 'http://example.com/quincy.jpg', '3940 Firwood Circle', '2023-10-20 04:00:00', 'Astronomy professor.', 'Observed three solar eclipses.', 'http://example.com/quincy_document.pdf', NULL, 0),
('Rachel', 'Sarah', 'Dawson', 'Female', '1982-06-10', 'rachel.dawson@email.com', 'rachelPassword!', 2345678918, 'http://example.com/rachel.jpg', '4142 Maplewood Court', '2023-10-20 05:00:00', 'Geography tutor.', 'Travelled to 50 countries.', 'http://example.com/rachel_document.pdf', 'email', 0),
('Steven', 'Thomas', 'Evans', 'Male', '1979-07-15', 'steven.evans@email.com', 'stevenPassword!', 2345678919, 'http://example.com/steven.jpg', '4344 Oakwood Drive', '2023-10-20 06:00:00', 'Drama instructor.', 'Acted in 10 plays.', 'http://example.com/steven_document.pdf', NULL, 0),
('Teresa', 'Ursula', 'Franklin', 'Female', '1980-08-05', 'teresa.franklin@email.com', 'teresaPassword!', 2345678920, 'http://example.com/teresa.jpg', '4546 Birchwood Plaza', '2023-10-20 07:00:00', 'Dance instructor.', 'Performed in 20 stage shows.', 'http://example.com/teresa_document.pdf', 'last_name', 0);


INSERT INTO Message (tid, sid, message, is_delete, time) VALUES
(1, 1, 'Hello, I would like to schedule a lesson with you for next week.', 0, '2023-11-01 10:00:00'),
(2, 1, 'I saw your profile and I am interested in learning more about Shakespeare.', 0, '2023-11-01 10:05:00'),
(1, 2, 'Can we reschedule our lesson from Monday to Wednesday?', 0, '2023-11-01 10:10:00'),
(3, 1, 'Thanks for the lesson today. It was very informative!', 0, '2023-11-01 10:15:00'),
(2, 3, 'Do you have any recommendations for literature books?', 0, '2023-11-01 10:20:00'),
(4, 2, 'I missed our lesson today due to an emergency. Can we catch up tomorrow?', 0, '2023-11-01 10:25:00'),
(5, 3, 'Can you help me with my homework assignment?', 0, '2023-11-01 10:30:00'),
(3, 2, 'I have a question about the topic we covered last week.', 0, '2023-11-01 10:35:00'),
(6, 1, 'Do you provide any discount for a package of 10 lessons?', 0, '2023-11-01 10:40:00'),
(7, 2, 'I will not be available next week. Can we pause our lessons?', 0, '2023-11-01 10:45:00'),
(8, 3, 'I have some doubts about our last lesson. Can we discuss?', 0, '2023-11-01 10:50:00'),
(4, 4, 'Are you available for a lesson this weekend?', 0, '2023-11-01 10:55:00'),
(9, 1, 'Your method of teaching is very effective. Thanks!', 0, '2023-11-01 11:00:00'),
(10, 5, 'Can I get a copy of the notes from our last lesson?', 0, '2023-11-01 11:05:00'),
(6, 4, 'I found a resource related to our topic. Would you like me to share?', 0, '2023-11-01 11:10:00'),
(7, 5, 'I need some additional resources for my project.', 0, '2023-11-01 11:15:00'),
(8, 4, 'Can we start with a new topic from next lesson?', 0, '2023-11-01 11:20:00'),
(9, 5, 'Thanks for the recommendation. The book was very helpful!', 0, '2023-11-01 11:25:00'),
(10, 6, 'I am facing some issues with the homework. Can you help?', 0, '2023-11-01 11:30:00'),
(5, 6, 'I would like to give some feedback about our lessons.', 0, '2023-11-01 11:35:00');


INSERT INTO CourseType (name, is_delete) VALUES
('Mathematics', 0),
('Science', 0),
('Literature', 0),
('History', 0),
('Arts', 0);

INSERT INTO Course (cid, tid, start_time, finish_time, location, is_delete) VALUES
(1,  1, '2023-11-13 09:00:00', '2023-11-13 10:00:00', 'Room A101', 0),
(1,  2, '2023-11-13 10:30:00', '2023-11-13 11:30:00', 'Room A102', 0),
(2,  3, '2023-11-14 09:00:00', '2023-11-14 10:00:00', 'Room B101', 0),
(2,  4, '2023-11-14 10:30:00', '2023-11-14 11:30:00', 'Room B102', 0),
(3,  5, '2023-11-15 09:00:00', '2023-11-15 10:00:00', 'Room C101', 0),
(3,  6, '2023-11-15 10:30:00', '2023-11-15 11:30:00', 'Room C102', 0),
(4,  7, '2023-11-16 09:00:00', '2023-11-16 10:00:00', 'Room D101', 0),
(4,  8, '2023-11-16 10:30:00', '2023-11-16 11:30:00', 'Room D102', 0),
(5,  9, '2023-11-17 09:00:00', '2023-11-17 10:00:00', 'Room E101', 0),
(5,  10, '2023-11-17 10:30:00', '2023-11-17 11:30:00', 'Room E102', 0),
(1,  1, '2023-11-18 09:00:00', '2023-11-18 10:00:00', 'Room A101', 0),
(2,  3, '2023-11-18 10:30:00', '2023-11-18 11:30:00', 'Room B102', 0),
(3,  5, '2023-11-19 09:00:00', '2023-11-19 10:00:00', 'Room C101', 0),
(4,  7, '2023-11-19 10:30:00', '2023-11-19 11:30:00', 'Room D102', 0),
(5,  9, '2023-11-20 09:00:00', '2023-11-20 10:00:00', 'Room E101', 0),
(1,  2, '2023-11-20 10:30:00', '2023-11-20 11:30:00', 'Room A102', 0),
(2,  4, '2023-11-21 09:00:00', '2023-11-21 10:00:00', 'Room B101', 0),
(3,  6, '2023-11-21 10:30:00', '2023-11-21 11:30:00', 'Room C102', 0),
(4,  8, '2023-11-22 09:00:00', '2023-11-22 10:00:00', 'Room D101', 0),
(5,  10, '2023-11-22 10:30:00', '2023-11-22 11:30:00', 'Room E102', 0);

INSERT INTO Appointment (csid, tid, sid, start_time, finish_time, is_delete) VALUES
(1, 1, 1, '2023-11-13 09:00:00', '2023-11-13 10:00:00', 0),
(2, 2, 1, '2023-11-13 10:30:00', '2023-11-13 11:30:00', 0),
(3, 3, 2, '2023-11-14 09:00:00', '2023-11-14 10:00:00', 0),
(4, 4, 3, '2023-11-14 10:30:00', '2023-11-14 11:30:00', 0),
(5, 5, 1, '2023-11-15 09:00:00', '2023-11-15 10:00:00', 0),
(6, 6, 2, '2023-11-15 10:30:00', '2023-11-15 11:30:00', 0),
(7, 7, 3, '2023-11-16 09:00:00', '2023-11-16 10:00:00', 0),
(8, 8, 1, '2023-11-16 10:30:00', '2023-11-16 11:30:00', 0),
(9, 9, 2, '2023-11-17 09:00:00', '2023-11-17 10:00:00', 0),
(10, 10, 3, '2023-11-17 10:30:00', '2023-11-17 11:30:00', 0),
(11, 1, 2, '2023-11-18 09:00:00', '2023-11-18 10:00:00', 0),
(12, 2, 3, '2023-11-18 10:30:00', '2023-11-18 11:30:00', 0),
(13, 3, 1, '2023-11-19 09:00:00', '2023-11-19 10:00:00', 0),
(14, 4, 2, '2023-11-19 10:30:00', '2023-11-19 11:30:00', 0),
(15, 5, 3, '2023-11-20 09:00:00', '2023-11-20 10:00:00', 0),
(16, 6, 1, '2023-11-20 10:30:00', '2023-11-20 11:30:00', 0),
(17, 7, 2, '2023-11-21 09:00:00', '2023-11-21 10:00:00', 0),
(18, 8, 3, '2023-11-21 10:30:00', '2023-11-21 11:30:00', 0),
(19, 9, 1, '2023-11-22 09:00:00', '2023-11-22 10:00:00', 0),
(20, 10, 2, '2023-11-22 10:30:00', '2023-11-22 11:30:00', 0);

-- Inserting into Rate table
INSERT INTO Rate (aid, rate, is_delete) VALUES
(1, 5, 0),
(2, 4, 0),
(3, 3, 0),
(4, 4, 0),
(5, 5, 0),
(6, 5, 0),
(7, 4, 0),
(8, 2, 0),
(9, 3, 0),
(10, 5, 0),
(11, 3, 0),
(12, 5, 0),
(13, 4, 0),
(14, 3, 0),
(15, 5, 0),
(16, 5, 0),
(17, 2, 0),
(18, 4, 0),
(19, 3, 0),
(20, 5, 0);

UPDATE Rate
SET feedback = 'This is a feedback!'
WHERE aid IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

UPDATE course
SET is_select = 0
WHERE is_select = null;

UPDATE appointment
SET approve = 0
WHERE approve = null;

UPDATE message
SET t_to_s = 0
WHERE t_to_s = null;

UPDATE tutor
SET time_zone = "GMT+11";

UPDATE student
SET time_zone = "GMT+11";

INSERT INTO Support (email, info, answer) VALUES 
('user1@example.com', 'I am having trouble logging in.', 'Please reset your password using the forgot password link.'),
('user2@example.com', 'My order has not arrived yet.', 'Your order is in transit and will arrive shortly.'),
('user3@example.com', 'How do I update my payment method?', 'You can update your payment method in the billing section of your account settings.'),
('user4@example.com', 'Can I change my subscription plan?', 'Yes, you can change your subscription plan from your account page.'),
('user5@example.com', 'The app is crashing on startup.', 'We have released an update to fix this issue. Please update your app.'),
('user6@example.com', 'I received the wrong item.', 'We apologize for the inconvenience. We will send the correct item immediately.'),
('user7@example.com', 'I want to cancel my subscription.', 'You can cancel your subscription in your account settings. If you need help, contact support.'),
('user8@example.com', 'Do you offer group discounts?', 'Yes, we offer discounts for groups larger than 10 people.'),
('user9@example.com', 'I cannot find the download link.', 'The download link has been sent to your email. Please check your spam folder.'),
('user10@example.com', 'My account was charged twice.', 'We have issued a refund for the duplicate charge. It should reflect in your account within 3-5 business days.'),
('user11@example.com', 'I have feedback about your service.', 'We appreciate your feedback and would love to hear more about your experience.'),
('user12@example.com', 'The video tutorial is not loading.', 'We are currently experiencing server issues. We expect to resolve them shortly.'),
('user13@example.com', 'Can I get a refund?', 'Our refund policy allows refunds within 30 days of purchase.'),
('user14@example.com', 'I need help with the setup.', 'We have a setup guide available in the help center, or you can contact support for direct assistance.'),
('user15@example.com', 'The latest update isn\'t working.', 'Please ensure that your device meets the minimum requirements for the update.'),
('user16@example.com', 'How do I delete my account?', 'Account deletion can be processed through the settings page, under privacy options.'),
('user17@example.com', 'Is my data secure?', 'We take data security seriously and use industry-standard encryption.'),
('user18@example.com', 'Can I pause my subscription?', 'Subscriptions can be paused at any time from your account dashboard.'),
('user19@example.com', 'The website is not accessible.', 'We are currently performing maintenance and expect to be back online shortly.'),
('user20@example.com', 'I forgot my username.', 'Your username is usually your email address. If further assistance is needed, contact support.');
