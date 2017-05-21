/*Reset*/
drop table students, lecturers, admins, groups, courses, groups_students, course_attendants, course_ownerships, activities, activity_types, activity_attendances, activity_grades, activity_files  CASCADE;

/*New*/
create table students (id  bigserial not null, email varchar(100) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, matricol varchar(50), primary key (id), unique(matricol));
alter table students add constraint UK_6dotkott2kjsp8vw410m25fb7 unique (email);
create table lecturers (id  bigserial not null, email varchar(100) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, website varchar(150), primary key (id));
alter table lecturers add constraint UK_6dotkoaa2kjsp8vw410m25fb7 unique (email);
create table admins (id  bigserial not null, email varchar(100) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id));
alter table admins add constraint UK_6dotkoaa2kjao8vw410m25fb7 unique (email);

create table groups (id  bigserial not null, name varchar(255) not null, year int4 not null, primary key (id));
alter table groups add constraint UK_8mf0is8024pqmwjxgldfe54l7 unique (name, year);
create table courses (id  bigserial not null, title varchar(255) not null, year int4 not null, semester int4 not null, nameid varchar(30), website varchar(150), primary key (id), unique(nameid));
alter table courses add constraint UK_8mf0is8024pqmwjxglfas54l7 unique (title);

create table activity_types (id  bigserial not null, name varchar(255) not null, primary key (id));
alter table activity_types add constraint UK_8mf0is8024pqgwjxztdfe14l7 unique (name);

create table activities (
  id bigserial not null,
  type_id int REFERENCES activity_types(id) ON UPDATE CASCADE ON DELETE CASCADE,
  date timestamp not null,
  name varchar(100) not null,
  course_id int REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
  primary key (id)
);


/*Linking Tables*/
CREATE TABLE groups_students (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE, group_id int REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT group_students_pkey PRIMARY KEY (student_id, group_id)
);
CREATE TABLE course_ownerships (
  lecturer_id    int REFERENCES lecturers (id) ON UPDATE CASCADE ON DELETE CASCADE, course_id int REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE, type varchar(30),
  CONSTRAINT course_ownerships_pkey PRIMARY KEY (lecturer_id, course_id)
);
CREATE TABLE course_attendants (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE, course_id int REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT course_attendants_pkey PRIMARY KEY (student_id, course_id)
);

/*Linking Activities Tables*/
CREATE TABLE activity_attendances (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE, activity_id int REFERENCES activities (id) ON UPDATE CASCADE,
  CONSTRAINT activity_attendances_pkey PRIMARY KEY (student_id, activity_id)
);
CREATE TABLE activity_grades (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE, activity_id int REFERENCES activities (id) ON UPDATE CASCADE, value numeric not null,
  CONSTRAINT activity_grades_pkey PRIMARY KEY (student_id, activity_id)
);
CREATE TABLE activity_files (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE not null, activity_id int REFERENCES activities (id) ON UPDATE CASCADE not null, file_name varchar(100) not null, extension varchar(30), file_id bigserial not null unique, upload_date timestamp not null,
  CONSTRAINT activity_files_pkey PRIMARY KEY (file_id)
);

/*View*/
CREATE OR REPLACE VIEW USERS AS SELECT * FROM(
  SELECT T1.id, T1.first_name, T1.last_name, T1.email, T1.password, 'student' as type FROM students T1
  UNION ALL
  SELECT T2.id, T2.first_name, T2.last_name, T2.email, T2.password, 'lecturer' as type FROM lecturers T2
  UNION ALL
  SELECT T3.id, T3.first_name, T3.last_name, T3.email, T3.password, 'admin' as type FROM admins T3
) as users;

CREATE OR REPLACE VIEW ACTIVITIES_JOIN AS SELECT * FROM(
  SELECT T1.id as user_id, 'student' as user_type, T2.id as activity_id, 'attendance' as role, -1 as extra_id FROM students T1, activities T2, activity_attendances T0 WHERE T1.id = T0.student_id AND T0.activity_id = T2.id
  UNION ALL
  SELECT T1.id as user_id, 'student' as user_type, T2.id as activity_id, 'grade' as role, -1 as extra_id FROM students T1, activities T2, activity_grades T0 WHERE T1.id = T0.student_id AND T0.activity_id = T2.id
  UNION ALL
  SELECT T1.id as user_id, 'student' as user_type, T2.id as activity_id, 'file' as role, T0.file_id as extra_id FROM students T1, activities T2, activity_files T0 WHERE T1.id = T0.student_id AND T0.activity_id = T2.id
) as activities_join;

/*Inserts*/
insert into admins (email, first_name, last_name, password) values ('admin@mail.com', 'Admin', 'Admin', '$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C'); /*$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C*/

insert into students (email, first_name, last_name, password) values ('ionut.iacob17@gmail.com','Ionut','Iacob','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('adascalitei.anca@gmail.com','Anca','Adascalitei','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('stefan.gordin@gmail.com','Stefan','Gordin','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('eveline.giosanu@gmail.com','Eveline','Giosanu','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('rares.stan@gmail.com','Rares','Stan','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('liana.tucar@gmail.com','Liana','Tucar','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('mihai.nechita@gmail.com','Mihai','Nechita','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');

insert into students (email, first_name, last_name, password) values ('sebastian.albisteanu@gmail.com','Sebastian','Albisteanu','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('vladimir.balan@gmail.com','Vladimir','Balan','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('ciprian.baetu@gmail.com','Ciprian','Baetu','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('sebastian.ciobanu@gmail.com','Sebastian','Ciobanu','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('catalina.jijie@gmail.com','Catalina','Jijie','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('dan.nastasa@gmail.com','Dan','Nastasa','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('ilinca.roman@gmail.com','Ilinca','Roman','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into students (email, first_name, last_name, password) values ('catalin.popusoi@gmail.com','Catalin','Popusoi','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');


insert into lecturers (email, first_name, last_name, password) values ('cfrasinaru@info.uaic.ro','Cristian','Frasinaru','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into lecturers (email, first_name, last_name, password) values ('olariu@gmail.ro','Florin','Olariu','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into lecturers (email, first_name, last_name, password) values ('dgavrilut@bitdefender.ro','Dragos','Gavrilut','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into lecturers (email, first_name, last_name, password) values ('ciortuz@info.uaic.ro','Liviu','Ciortuz','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');

insert into lecturers (email, first_name, last_name, password) values ('ftiplea@info.uaic.ro','Ferucio','Tiplea','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into lecturers (email, first_name, last_name, password) values ('sburaga@info.uaic.ro','Sabin','Buraga','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');
insert into lecturers (email, first_name, last_name, password) values ('anca.nica@info.uaic.ro','Anca','Nica','$2a$10$I.6VrNIWMDCiUkBAZ95n9OtrrdS7KyzrcWNPbsa24xaX6z1W/MV.C');


/*Courses and groups*/
insert into courses (title, year, semester) values ('Java - Programare Avansata',2,2);
insert into courses (title, year, semester) values ('Introducere in .NET', 3, 1);
insert into courses (title, year, semester) values ('Tehnici de programare pe platforma Android', 3, 1);
insert into courses (title, year, semester) values ('Machine Learning', 3, 1);
insert into courses (title, year, semester) values ('Securitatea Informatiei', 3, 1);
insert into courses (title, year, semester) values ('Tehnologii Web', 2, 2);

insert into groups (name, year) values ('A4',3);
insert into groups (name, year) values ('A5',3);
insert into groups (name, year) values ('A6',3);
insert into groups (name, year) values ('Licenta 2016-2017', 3);

insert into groups_students (student_id, group_id) values (1,2);
insert into groups_students (student_id, group_id) values (1,4);
insert into groups_students (student_id, group_id) values (2,2);
insert into groups_students (student_id, group_id) values (2,4);
insert into groups_students (student_id, group_id) values (3,2);
insert into groups_students (student_id, group_id) values (4,2);
insert into groups_students (student_id, group_id) values (4,4);
insert into groups_students (student_id, group_id) values (5,1);
insert into groups_students (student_id, group_id) values (6,1);
insert into groups_students (student_id, group_id) values (7,1);

insert into course_attendants (student_id, course_id) values (1,1);
insert into course_attendants (student_id, course_id) values (1,2);
insert into course_attendants (student_id, course_id) values (1,4);
insert into course_attendants (student_id, course_id) values (1,5);
insert into course_attendants (student_id, course_id) values (1,6);
insert into course_attendants (student_id, course_id) values (2,1);
insert into course_attendants (student_id, course_id) values (2,2);
insert into course_attendants (student_id, course_id) values (2,4);
insert into course_attendants (student_id, course_id) values (2,5);
insert into course_attendants (student_id, course_id) values (2,6);
insert into course_attendants (student_id, course_id) values (3,1);
insert into course_attendants (student_id, course_id) values (3,2);
insert into course_attendants (student_id, course_id) values (3,3);
insert into course_attendants (student_id, course_id) values (3,4);
insert into course_attendants (student_id, course_id) values (4,1);
insert into course_attendants (student_id, course_id) values (4,4);
insert into course_attendants (student_id, course_id) values (4,5);
insert into course_attendants (student_id, course_id) values (4,6);
insert into course_attendants (student_id, course_id) values (5,1);
insert into course_attendants (student_id, course_id) values (5,2);
insert into course_attendants (student_id, course_id) values (5,3);
insert into course_attendants (student_id, course_id) values (5,5);
insert into course_attendants (student_id, course_id) values (6,1);
insert into course_attendants (student_id, course_id) values (6,2);
insert into course_attendants (student_id, course_id) values (6,3);
insert into course_attendants (student_id, course_id) values (6,4);
insert into course_attendants (student_id, course_id) values (6,5);
insert into course_attendants (student_id, course_id) values (7,1);
insert into course_attendants (student_id, course_id) values (7,2);
insert into course_attendants (student_id, course_id) values (7,3);
insert into course_attendants (student_id, course_id) values (7,4);
insert into course_attendants (student_id, course_id) values (7,5);
insert into course_attendants (student_id, course_id) values (7,6);


insert into course_ownerships (lecturer_id, course_id, type) values (1,1,'owner');
insert into course_ownerships (lecturer_id, course_id, type) values (2,2,'owner');
insert into course_ownerships (lecturer_id, course_id, type) values (3,3,'owner');
insert into course_ownerships (lecturer_id, course_id, type) values (4,4,'owner');
insert into course_ownerships (lecturer_id, course_id, type) values (5,5,'owner');
insert into course_ownerships (lecturer_id, course_id, type) values (7,5,'assistant');
insert into course_ownerships (lecturer_id, course_id, type) values (6,6,'owner');

/*Activities & Types*/
insert into activity_types (name) values ('curs');
insert into activity_types (name) values ('laborator');
insert into activity_types (name) values ('seminar');
insert into activity_types (name) values ('proiect');
insert into activity_types (name) values ('test');
insert into activity_types (name) values ('examen');
insert into activity_types (name) values ('colocviu');

insert into activities(type_id, date, course_id, name) values (1,TIMESTAMP '2017-01-17 15:36:38',1,'Curs 1');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-17 15:36:38',1,'Laborator 1');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 2');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 3');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 4');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 5');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 6');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 7');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 8');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 9');
insert into activities(type_id, date, course_id, name) values (2,TIMESTAMP '2017-01-18 15:36:38',1,'Laborator 10');
insert into activities(type_id, date, course_id, name) values (6,TIMESTAMP '2017-01-26 14:00:00',4,'Partial 2');
insert into activity_attendances(student_id, activity_id) VALUES (1,1);
insert into activity_attendances(student_id, activity_id) VALUES (1,2);
insert into activity_attendances(student_id, activity_id) VALUES (1,3);
insert into activity_attendances(student_id, activity_id) VALUES (1,4);
insert into activity_attendances(student_id, activity_id) VALUES (1,5);
insert into activity_attendances(student_id, activity_id) VALUES (1,6);
insert into activity_attendances(student_id, activity_id) VALUES (1,7);
insert into activity_attendances(student_id, activity_id) VALUES (1,8);
insert into activity_attendances(student_id, activity_id) VALUES (1,9);
insert into activity_attendances(student_id, activity_id) VALUES (1,10);
insert into activity_attendances(student_id, activity_id) VALUES (1,11);
insert into activity_attendances(student_id, activity_id) VALUES (2,2);
insert into activity_attendances(student_id, activity_id) VALUES (2,12);
insert into activity_grades(student_id, activity_id, value) VALUES (1,2,8);
insert into activity_grades(student_id, activity_id, value) VALUES (1,3,9);
insert into activity_grades(student_id, activity_id, value) VALUES (1,4,7);
insert into activity_grades(student_id, activity_id, value) VALUES (2,3,10);
insert into activity_grades(student_id, activity_id, value) VALUES (2,4,10);
insert into activity_files(student_id, activity_id, file_name, extension, upload_date) VALUES (1,2,'test','txt',TIMESTAMP '2017-01-26 14:00:00');


/*Sample Selects*/
-- select students.first_name, students.last_name, activities.date, activity_types.name from students, activities, activity_attendances, activity_types where activity_attendances.student_id = students.id and activities.id = activity_attendances.activity_id and activities.type_id = activity_types.id;
