create table groups (id  bigserial not null, name varchar(255) not null, year_of_study int4 not null, primary key (id))
create table users (id  bigserial not null, email varchar(30) not null, first_name varchar(30) not null, last_name varchar(30) not null, password varchar(60) not null, type varchar(255) not null, primary key (id))
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
