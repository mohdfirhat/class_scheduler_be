INSERT INTO department 
	(name)
VALUES 
	('Computer Science'),
	('English'),
	('History'),
	('Science');

INSERT INTO course
	(department_id,name,course_code)
VALUES
	(1,'Programming Fundamentals','CS101'),
	(1,'Object-Oriented Programming','CS102'),
	(1,'Data Structures and Algorithms','CS103'),
	(1,'Database Systems','CS201'),
	(1,'Web Development','CS202'),
	(1,'Software Engineering','CS203');

INSERT INTO venue 
  (name,description,address,img_url,occupancy)
VALUES
  ('LT-01','Lecture Hall 1','Address 1', 'http://localhost:8000/img1',10),
  ('LT-02','Lecture Hall 2','Address 2', 'http://localhost:8000/img2',20),
  ('LT-03','Lecture Hall 3','Address 3', 'http://localhost:8000/img3',30),
  ('LT-04','Lecture Hall 4','Address 4', 'http://localhost:8000/img4',40),
  ('LT-05','Lecture Hall 5','Address 5', 'http://localhost:8000/img5',50);

INSERT INTO teacher 
	(department_id, manager_id ,first_name,last_name,email)
VALUES 
	(1,NULL,'Ada','Lovelace','ada.lovelace@gmail.com'),
	(1,1,'Alan','Turing','alan.turing@gmail.com'),
	(1,1,'Grace','Hopper','grace.hopper@gmail.com'),
	(1,1,'Linus','Torvalds','linus.torvalds@gmail.com');

INSERT INTO teachers_courses
	(teacher_id,course_id)
VALUES
	(2,1),
	(2,2),
	(2,3),
	(2,4),
	(2,5),
	(2,6),
    (3,1),
	(3,2),
	(3,3),
	(3,4),
	(3,5),
	(4,1),
	(4,2),
	(4,3),
	(4,4);

INSERT INTO section_status
    (id,type)
VALUES
    (1,'pending'),
    (2,'approved'),
    (3,'rejected');

INSERT INTO timeslot
    (id,start_time,end_time)
VALUES
    (1,'08:00:00','10:00:00'),
    (2,'10:00:00','12:00:00'),
    (3,'13:00:00','15:00:00'),
    (4,'15:00:00','17:00:00');

INSERT INTO section
	(teacher_id,venue_id,course_id,section_status_id,timeslot_id,name,date,class_size)
VALUES 
	(2,1,1,2,1,'lesson 1','2025-10-20`',10),
	(3,1,1,2,1,'lesson 2','2025-10-20',10),
	(2,1,1,2,2,'lesson 3','2025-10-20',10),
	(3,1,1,2,3,'lesson 4','2025-10-20', 10),
	(4,1,1,2,4,'lesson 5','2025-10-20', 10);

INSERT INTO teacher_leave_status
(id,type)
VALUES
    (1,'pending'),
    (2,'approved'),
    (3,'rejected');

INSERT INTO teacher_leave
    (teacher_id,teacher_leave_status_id,start_date,end_date)
VALUES
    (2,1,'2025-10-20','2025-10-20'),
    (3,1,'2025-10-20','2025-10-20');