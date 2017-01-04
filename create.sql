create table admins (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table course_ownerships (lecturer_id int8 not null, course_id int8 not null)
create table courses (id  bigserial not null, semester int4 not null, title varchar(255) not null, year int4 not null, primary key (id))
create table groups (id  bigserial not null, name varchar(255) not null, year int4 not null, primary key (id))
create table groups_students (student_id int8 not null, group_id int8 not null)
create table lecturers (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table students (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, primary key (id))
create table users (v_id varchar(255) not null, email varchar(30) not null, first_name varchar(30) not null, id int8 not null, last_name varchar(30) not null, password varchar(60) not null, type varchar(255) not null, primary key (v_id))
alter table admins add constraint UK_47bvqemyk6vlm0w7crc3opdd4 unique (email)
alter table courses add constraint UK_pag0ngrmsyx23ii8bnu9k0438 unique (title)
alter table groups add constraint UK_8mf0is8024pqmwjxgldfe54l7 unique (name)
alter table lecturers add constraint UK_843xkfafq5501ytxnj4p5w85j unique (email)
alter table students add constraint UK_e2rndfrsx22acpq2ty1caeuyw unique (email)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table course_ownerships add constraint FKdteyi0kxavjai2uqmllxfr41y foreign key (course_id) references groups
alter table course_ownerships add constraint FKc76c4j918h258tiyi1p8iifxf foreign key (lecturer_id) references lecturers
alter table groups_students add constraint FK8jhg7j929xlnbng72mj0ccjvd foreign key (group_id) references groups
alter table groups_students add constraint FKi1o84xqmadvtx69xcfumj2lrt foreign key (student_id) references students
