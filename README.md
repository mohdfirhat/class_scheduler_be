# Lesson Scheduler Backend

## Table of Content

- [Description](#description)
- [Technology Used](#teachnology-used)
- 

## Description

The backend for TFIP to manage teachers schedules such as leaves and sections. Managers are able to login and carry out the following features:

- View teacher details
- View leave details
- View section details
- View teacher schedules
- Create sections
- Approve/Reject Leaves
- Resolve teacher leave conflict (with sections) by reassigning teachers

## Teachnology Used

- [Spring Boot](http://projects.spring.io/spring-boot/)

## Endpoints

### Teacher

#### GET

- http://localhost:8080/api/teachers/{managerId}/courses

## Requirements

- [MariaDB version 11.4](https://mariadb.org/)
- [JDK 17](https://www.oracle.com/asean/java/technologies/downloads/)

## Local Setup

Run the following codes in the terminal to set up the project, create the 
database/tables/data and start up the springboot application.

```bash
# Step 1: If you are at the root folder, change directory to the resource folder
cd src/main/resources

# Step 2: Create a copy of the application.properties
cp application.properties.example application.properties

# Step 3: Modify the application.properties
# change the `spring.datasource.username` to your username
# change the `spring.datasource.password` to your password
# save changes with CTRL + X, Y and ENTER
nano application.properties

# Step 4: Login to mariaDB
mariadb -u <USERNAME> -p

# Step 5: Run lessonscheduler_DDL.sql to create database 
# (called `lessonscheduler`) with all the required tables
SOURCE lessonscheduler_DDL.sql

# Step 6: Run lessonscheduler_DML.sql to seed the database
SOURCE lessonscheduler_DML.sql

# Step 7: Travest to the root of project folder
cd ../../.. 

# Step 8: Run the server
./mvnw spring-boot:run

# If maven is installed globally (run this instead)
mvn spring-boot:run

```
