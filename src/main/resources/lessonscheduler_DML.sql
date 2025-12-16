DELETE FROM teacher_leave;
DELETE FROM venue;
DELETE FROM timeslot;
DELETE FROM teacher_leave_status;
DELETE FROM section_status;
DELETE FROM teachers_courses;
DELETE FROM teacher;
DELETE FROM section;
DELETE FROM course;
DELETE FROM department;

INSERT INTO department(id, name) 
VALUES 
	(1, "Computer Science"),
	(2, "English"),
	(3, "History"),
	(4, "Science");

INSERT INTO course 
	(id, department_id,name,course_code, description)
VALUES
	(1, 1,'Programming Fundamentals','CS101', 'Introduction class to Programming Fundamentals'),
	(2, 1,'Object-Oriented Programming','CS102', 'Basics of Object Oriented Programming'),
	(3, 1,'Data Structures and Algorithms','CS103', 'Basics of Data Structures and Algorithms'),
	(4, 1,'Database Systems','CS201', 'Advanced techniques in managing database systems'),
	(5, 1,'Web Development','CS202', 'Introduction class to Web Development'),
	(6, 1,'Software Engineering','CS203', 'Software engineering fundamentals');
	
INSERT INTO venue 
  (id, name,description,address,img_url,occupancy)
VALUES
  (1, 'LT-01','Lecture Hall 1','Address 1', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ81zN11UU2EDWb0_-bF0RHrOzl78JBqn7JCg&s',10),
  (2, 'LT-02','Lecture Hall 2','Address 2', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRst3lNS5KhzWqIsC00OaT15ncUnLRpwnd3gg&s',20),
  (3, 'LT-03','Lecture Hall 3','Address 3', 'https://onesim-production.s3.ap-southeast-1.amazonaws.com/onesim/media/pd-campus-locations-and-facilities/sim%20mh/el-gallery-lecture-theatre.jpg?ext=.jpg',30),
  (4, 'LT-04','Lecture Hall 4','Address 4', 'https://www.venuexplorer.com.sg/uploads/LLI_LectureHall_VX.jpg',40),
  (5, 'LT-05','Lecture Hall 5','Address 5', 'https://moody.utexas.edu/sites/default/files/2021-09/dmc1202_6345_2015_main.jpeg',50);

INSERT INTO teacher 
	(id, department_id, manager_id ,first_name,last_name,email, avatar_url)
VALUES 
	(1, 1,NULL,'Ada','Lovelace','ada.lovelace@gmail.com', null),
	(2, 1,1,'Alan','Turing','alan.turing@gmail.com', 'https://t3.ftcdn.net/jpg/02/43/12/34/360_F_243123463_zTooub557xEWABDLk0jJklDyLSGl2jrr.jpg'),
	(3, 1,1,'Grace','Hopper','grace.hopper@gmail.com', 'https://ichef.bbci.co.uk/ace/standard/976/cpsprodpb/C597/production/_131938505_ind3bc40c5f1c10d4248e6bf848ae7033c8814005e9-1.jpg'),
	(4, 1,1,'Linus','Torvalds','linus.torvalds@gmail.com', null),
   (5, 1,1,'Bob','Marley','bob.marley@gmail.com', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmrev9Q6ElA9o8pOkbiTNIaGDXmdc1SrnNQQ&s'),
   (6, 1,1,'Isha','Parker','isha.parker@gmail.com', 'https://media.istockphoto.com/id/1299077558/photo/lead-yourself-to-a-life-of-success.jpg?s=612x612&w=0&k=20&c=OQZPSnM1Eq-4Xx8bxJE8KQ5olJFfRw_YMc29aQ0Au6U='),
   (7, 1,1,'Masako','Bellini','masako.bellini@gmail.com', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBuQi_p0Q1vlxn4R-Ebq1ZVX7GyzgBEqMmmw&s'),
   (8, 1,1,'Adam','Lindqvist','adam.lindqvist@gmail.com', 'https://thumbs.dreamstime.com/b/portrait-handsome-smiling-young-man-folded-arms-smiling-joyful-cheerful-men-crossed-hands-isolated-studio-shot-172869765.jpg'),
   (9, 1,1,'Jun Jie','Tan','junjie.tan@gmail.com', 'https://media.istockphoto.com/id/1288538088/photo/portrait-young-confident-smart-asian-businessman-look-at-camera-and-smile.jpg?s=612x612&w=0&k=20&c=qkOwVHZFC-fbtbTnufVGaXFhnQBcfEjzbu5ThSXVLR0='),
   (10, 1,1,'Hui En','Lin','huien.lin@gmail.com', 'https://www.shutterstock.com/image-photo/young-chinese-woman-smiling-confident-600nw-2331522413.jpg');

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
	(4,4),
	
	(5,1),
	(5,2),
	(5,3),
	(5,4),
	
	(6,1),
	(6,2),
	(6,3),
	(6,4),
	
	(7,1),
	(7,2),
	(7,3),
	(7,4),
	
	(8,1),
	(8,2),
	(8,3),
	(8,4),
	
	(9,1),
	(9,2),
	(9,3),
	(9,4),
	
	(10,1),
	(10,2),
	(10,3),
	(10,4),
	(10,5),
	(10,6);

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
	(id, teacher_id,venue_id,course_id,section_status_id,timeslot_id,remark,date,class_size)
VALUES 
	(1,2,1,1,2,1,'lesson 1','2025-12-20`',10),
	(2,3,1,2,2,1,'lesson 2','2025-12-20',10),
	(3,2,1,2,2,2,'lesson 3','2025-12-20',10),
	(4,3,1,3,2,3,'lesson 4','2025-12-20', 10),
	(5,4,1,3,2,4,'lesson 5','2025-12-20', 10),
   (6,4,1,2,1,4,'lesson 6','2025-12-21', 40),
   (7,2,1,4,1,4,'lesson 7','2025-12-22', 30),
   (8,2,1,4,1,4,'lesson 8','2025-12-22', 20),
   (9,10,2,6,2,3, 'SWE test', '2025-12-10', 20);

INSERT INTO teacher_leave_status
    (id, type)
VALUES
    (1, 'pending'),
    (2, 'approved'),
    (3, 'rejected');

INSERT INTO teacher_leave
    (id,teacher_id,teacher_leave_status_id,start_date,end_date)
VALUES
    (1,3,1,'2025-10-20','2025-10-20'),
    (2,2,1, '2025-10-12', '2025-10-13'),
    (3,2,1, '2025-10-19', '2025-10-22'),
    (4,5,1, '2025-12-13', '2025-12-20'),
    (5,5,2, '2025-11-19', '2025-11-22'),
    (6,5,3, '2025-11-19', '2025-11-29'),
    (7,2,1, '2025-12-19', '2025-12-23'),
    (8,3,1, '2025-12-01', '2025-10-29'),
    (9,4,1, '2025-12-01', '2025-10-15'),
	 (10,10,1, '2025-12-10', '2025-12-15');

    
