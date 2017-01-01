create table groups (id  bigserial not null, name varchar(255) not null, year_of_study int4 not null, primary key (id))
create table usergroup (user_id int8 not null, group_id int8 not null, primary key (user_id, group_id))
create table users (dtype varchar(31) not null, id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, type varchar(255) not null, primary key (id))
alter table groups add constraint UK_8mf0is8024pqmwjxgldfe54l7 unique (name)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)


/*Reset*/
drop table students, lecturers, admins CASCADE;

/*New*/
create table students (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id));
alter table students add constraint UK_6dotkott2kjsp8vw410m25fb7 unique (email);
create table lecturers (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id));
alter table lecturers add constraint UK_6dotkoaa2kjsp8vw410m25fb7 unique (email);
create table admins (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id));
alter table admins add constraint UK_6dotkoaa2kjao8vw410m25fb7 unique (email);
create table groups (id  bigserial not null, name varchar(255) not null, year int4 not null, primary key (id));
alter table groups add constraint UK_8mf0is8024pqmwjxgldfe54l7 unique (name);

insert into admins (email, first_name, last_name, password) values ('admin@mail.com', 'admin', 'admin', '$2a$10$EPecQyZ6HY2lC4QZQDALXeToh8765r.YSyylmB3KOFeQsGmhIPq42');

/*Linking Tables*/
CREATE TABLE groups_students (
  student_id    int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE, group_id int REFERENCES groups (id) ON UPDATE CASCADE,
  CONSTRAINT group_students_pkey PRIMARY KEY (student_id, group_id)
);


/*View*/
CREATE OR REPLACE VIEW USERS AS SELECT * FROM(
  SELECT T1.id, T1.first_name, T1.last_name, T1.email, T1.password, 'student' as type, 'S'||T1.id as v_id FROM students T1
  UNION ALL
  SELECT T2.id, T2.first_name, T2.last_name, T2.email, T2.password, 'lecturer' as type, 'L'||T2.id as v_id FROM lecturers T2
  UNION ALL
  SELECT T3.id, T3.first_name, T3.last_name, T3.email, T3.password, 'admin' as type, 'A'||T3.id as v_id FROM admins T3
) as users;