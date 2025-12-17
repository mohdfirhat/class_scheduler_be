-- CREATE DATABASE
DROP DATABASE IF EXISTS lessonscheduler;
CREATE DATABASE IF NOT EXISTS lessonscheduler;

-- USE DATABASE
USE lessonscheduler;

-- DROP TABLES
DROP TABLE IF EXISTS section,
                    teachers_courses,
                    teacher_leave,
                    teacher,
                    course,
                    department,
                    section_status,
                    teacher_leave_status,
                    timeslot,
                    venue;

CREATE TABLE venue (
--	Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	description VARCHAR(1000) DEFAULT NULL,
	img_url VARCHAR(255) DEFAULT NULL,
	occupancy INT(11) NOT NULL,
-- 	Constraints
	PRIMARY KEY(id)
);

-- CREATE TABLES
CREATE TABLE timeslot (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	start_time TIME(6) NOT NULL,
	end_time TIME(6) NOT NULL,
-- Constraints
	PRIMARY KEY(id),
	UNIQUE KEY(start_time),
	UNIQUE KEY(end_time)
);

CREATE TABLE teacher_leave_status (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(20) NOT NULL,
-- Constraints
	PRIMARY KEY(id),
	UNIQUE KEY(`type`)
);

CREATE TABLE section_status (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(20) NOT NULL,
-- Constraints
	PRIMARY KEY(id),
	UNIQUE KEY(`type`)
);

CREATE TABLE department (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
-- Constraints
	PRIMARY KEY(id),
	UNIQUE KEY(NAME)
);

CREATE TABLE course (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	department_id BIGINT(20) NOT NULL,
	name VARCHAR(255) NOT NULL,
	course_code CHAR(5) NOT NULL,
	description VARCHAR(1000) DEFAULT NULL,
-- Constraints
	PRIMARY KEY(id),
    UNIQUE KEY (course_code),
	CONSTRAINT course_FK_department_id FOREIGN KEY(department_id) REFERENCES department(id) ON DELETE CASCADE
);

CREATE TABLE teacher (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	manager_id BIGINT(20) DEFAULT NULL,
	department_id BIGINT(20) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	leave_days INT(11) NOT NULL DEFAULT 14,
	avatar_url VARCHAR(255) DEFAULT NULL,
-- Constraints
	PRIMARY KEY(id),
    UNIQUE KEY (email),
	CONSTRAINT teacher_FK_manager_id FOREIGN KEY(manager_id) REFERENCES teacher(id) ON DELETE SET NULL,
	CONSTRAINT teacher_FK_department_id FOREIGN KEY(department_id) REFERENCES department(id) ON DELETE CASCADE
);


CREATE TABLE teacher_leave (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	teacher_id BIGINT(20) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	teacher_leave_status_id BIGINT(20) NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT teacher_leave_FK_teacher_id FOREIGN KEY(teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
	CONSTRAINT teacher_leave_FK_teacher_leave_status_id FOREIGN KEY(teacher_leave_status_id) REFERENCES teacher_leave_status(id) ON DELETE CASCADE
);

CREATE TABLE teachers_courses (
--	Variables
    teacher_id BIGINT(20) NOT NULL,
    course_id BIGINT(20) NOT NULL,
-- 	Constraints
    PRIMARY KEY (teacher_id, course_id),
    CONSTRAINT teacher_courses_FK_teacher_id FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
    CONSTRAINT teacher_courses_FK_course_id FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

CREATE TABLE section (
-- Variables
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	section_status_id BIGINT(20) NOT NULL,
	teacher_id BIGINT(20) NOT NULL,
	venue_id BIGINT(20) NOT NULL,
	course_id BIGINT(20) NOT NULL,
	timeslot_id BIGINT(20) NOT NULL,
	remark VARCHAR(1000) DEFAULT NULL,
	class_size INT(11) NOT NULL,
	`date` DATE NOT NULL, 
-- Constraints
	PRIMARY KEY(id),
	CONSTRAINT section_FK_teacher_id FOREIGN KEY(teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
	CONSTRAINT section_FK_venue_id FOREIGN KEY(venue_id) REFERENCES venue(id) ON DELETE CASCADE,
	CONSTRAINT section_FK_timeslot_id FOREIGN KEY(timeslot_id) REFERENCES timeslot(id) ON DELETE CASCADE,
	CONSTRAINT section_FK_section_status_id FOREIGN KEY(section_status_id) REFERENCES section_status(id) ON DELETE CASCADE,
	CONSTRAINT section_FK_course_id FOREIGN KEY(course_id) REFERENCES course(id) ON DELETE CASCADE
);















