create table if not exists users(
  username varchar(64) not null primary key,
  password varchar(64) not null,
  email varchar(128),
  firstName varchar(128) not null,
  lastName varchar(128) not null,
  birthDate DATE not null );

create table if not exists foo(
  id bigint not null primary key,  
  name varchar(128)
);

delete from users;
insert into users(username,password,email, firstName, lastName,birthDate) values('user1','changeme','jean.premier@example.com', 'Jean', 'Premier', PARSEDATETIME('1970-01-01','yyyy-MM-dd'));
insert into users(username,password,email, firstName, lastName,birthDate) values('user2','changeme','louis.second@example.com','Louis', 'Second', PARSEDATETIME('1970-01-01','yyyy-MM-dd'));
insert into users(username,password,email, firstName, lastName,birthDate) values('user3','changeme','philippe.troisieme@example.com','Philippe', 'Troisième', PARSEDATETIME('1970-01-01','yyyy-MM-dd'));

INSERT INTO foo(id, name) VALUES (1, 'Foo 1');
INSERT INTO foo(id, name) VALUES (2, 'Foo 2');
INSERT INTO foo(id, name) VALUES (3, 'Foo 3');

create table if not exists users_details(
  username varchar(64) not null primary key,
  name varchar(64) not null,
  phone_no varchar(255) not null,
  addr varchar(255) not null,
  city varchar(255) not null,
  bio varchar(1000) not null
  );
delete from users_details;
insert into users_details(username,name,phone_no, addr, city, bio) values('user1','바이든','010-1111-2222', '마당 넓은 집', '미국', '하이요');
insert into users_details(username,name,phone_no, addr, city, bio) values('user2','윤석열','010-3333-4444', '용산 청와대', '대한민국', '하이요');
insert into users_details(username,name,phone_no, addr, city, bio) values('user3','시진핑','010-5555-6666', '공산당 남바원', '중국', '하이요');
