CREATE TABLE IF NOT EXISTS  users (
  username VARCHAR(64) NOT NULL PRIMARY KEY,
  password VARCHAR(64) NOT NULL,
  email VARCHAR(128),
  firstName VARCHAR(128) NOT NULL,
  lastName VARCHAR(128) NOT NULL,
  birthDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS foo (
  id BIGINT NOT NULL PRIMARY KEY,  
  name VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS users_details (
  username VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  phone_no VARCHAR(255) NOT NULL,
  addr VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  bio VARCHAR(255) NOT NUll
);

DELETE FROM users;
INSERT INTO users(username,password,email, firstName, lastName,birthDate) VALUES('user1','changeme','jean.premier@example.com', 'Jean', 'Premier', '1970-01-01');
INSERT INTO users(username,password,email, firstName, lastName,birthDate) VALUES('user2','changeme','louis.second@example.com','Louis', 'Second', '1970-01-01');
INSERT INTO users(username,password,email, firstName, lastName,birthDate) VALUES('user3','changeme','philippe.troisieme@example.com','Philippe', 'Troisième', '1970-01-01');

DELETE FROM foo;
INSERT INTO foo(id, name) VALUES (1, 'Foo 1');
INSERT INTO foo(id, name) VALUES (2, 'Foo 2');
INSERT INTO foo(id, name) VALUES (3, 'Foo 3');

DELETE FROM users_details;
INSERT INTO users_details(username,name,phone_no, addr, city, bio) VALUES('user1','바이든','010-1111-2222', '마당 넓은 집', '미국', '오우 미쿡 세계 최강 남바완');
INSERT INTO users_details(username,name,phone_no, addr, city, bio) VALUES('user2','윤석열','010-3333-4444', '용산 청와대', '대한민국', '오우 두유 노우 헬조선?');
INSERT INTO users_details(username,name,phone_no, addr, city, bio) VALUES('user3','시진핑','010-5555-6666', '공산당 남바원', '중국', '니취팔로마');
