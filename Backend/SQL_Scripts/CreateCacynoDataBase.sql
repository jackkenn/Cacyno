start transaction;

create user if not exists 'admin'@'localhost' identified by 'password';
grant all privileges on *.* to 'admin'@'localhost';
drop database if exists cacyno;
create database cacyno;
use cacyno;

create table user (
	id binary(16) unique not null,
    username varchar(50) not null,
    money int,
    primary key(id)
);

commit;