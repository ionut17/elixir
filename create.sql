create table activities (id  bigserial not null, date timestamp not null, name varchar(100) not null, course_id int8 not null, type_id int8 not null, primary key (id))
create table activities_join (activity_id int8 not null, role varchar(100) not null, user_id int8 not null, user_type varchar(255) not null, primary key (activity_id, role, user_id, user_type))
create table activity_attendances (activity_id int8 not null, student_id int8 not null, primary key (activity_id, student_id))
create table activity_files (activity_id int8 not null, student_id int8 not null, extension varchar(255) not null, file_id int8 not null, file_name varchar(255) not null, primary key (activity_id, student_id))
create table activity_grades (activity_id int8 not null, student_id int8 not null, value int4 not null, primary key (activity_id, student_id))
create table activity_types (id  bigserial not null, name varchar(255) not null, primary key (id))
create table admins (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table course_attendants (student_id int8 not null, course_id int8 not null)
create table course_ownerships (lecturer_id int8 not null, course_id int8 not null)
create table courses (id  bigserial not null, semester int4 not null, title varchar(255) not null, year int4 not null, primary key (id))
create table groups (id  bigserial not null, name varchar(255) not null, year int4 not null, primary key (id))
create table groups_students (student_id int8 not null, group_id int8 not null)
create table lecturers (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table students (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table users (id int8 not null, type varchar(255) not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id, type))
alter table activity_types add constraint UK_36uexkiww6jkyqnxwjl5a9mmy unique (name)
alter table admins add constraint UK_47bvqemyk6vlm0w7crc3opdd4 unique (email)
alter table courses add constraint UK_pag0ngrmsyx23ii8bnu9k0438 unique (title)
alter table groups add constraint UK_8mf0is8024pqmwjxgldfe54l7 unique (name)
alter table lecturers add constraint UK_843xkfafq5501ytxnj4p5w85j unique (email)
alter table students add constraint UK_e2rndfrsx22acpq2ty1caeuyw unique (email)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table activities add constraint FK2ffbfqepfc7d708tgo77g4n6d foreign key (course_id) references courses
alter table activities add constraint FKrfrixuyeyeh9u3wy36hj3pid5 foreign key (type_id) references activity_types
alter table activities_join add constraint FK5b6wls29jq4u3u1iv299n8req foreign key (activity_id) references activities
alter table activities_join add constraint FKfps83a155e2cj32r8gustl22r foreign key (user_id, user_type) references users
alter table activity_attendances add constraint FKii9npa9oxy9sfyyd1a67uld0a foreign key (activity_id) references activities
alter table activity_attendances add constraint FKrm0lea22nt12yoaevnugwvuss foreign key (student_id) references students
alter table activity_files add constraint FKcm0vyu610nhvgoeyu0dcqrpx4 foreign key (activity_id) references activities
alter table activity_files add constraint FKp0uhxw3v8lrcux8iuu5q5dr9v foreign key (student_id) references students
alter table activity_grades add constraint FK4ud2h9ansq8o1c64mjlgitl9k foreign key (activity_id) references activities
alter table activity_grades add constraint FKpalbmehq99kkugb5cs3huj5i foreign key (student_id) references students
alter table course_attendants add constraint FKdw865xwqemphyjtcko0oqs4nq foreign key (course_id) references courses
alter table course_attendants add constraint FK92bkhfafrooir41sg3jha32n foreign key (student_id) references students
alter table course_ownerships add constraint FKtjpjx2vqkjfvv2emflglf7umn foreign key (course_id) references courses
alter table course_ownerships add constraint FKc76c4j918h258tiyi1p8iifxf foreign key (lecturer_id) references lecturers
alter table groups_students add constraint FK8jhg7j929xlnbng72mj0ccjvd foreign key (group_id) references groups
alter table groups_students add constraint FKi1o84xqmadvtx69xcfumj2lrt foreign key (student_id) references students
