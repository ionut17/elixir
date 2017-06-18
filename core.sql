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

/*Activities & Types*/
insert into activity_types (name) values ('curs');
insert into activity_types (name) values ('laborator');
insert into activity_types (name) values ('seminar');
insert into activity_types (name) values ('proiect');
insert into activity_types (name) values ('test');
insert into activity_types (name) values ('examen');
insert into activity_types (name) values ('colocviu');