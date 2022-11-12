create database if not exists blog_system;

use blog_system;

drop table if exists blog;
create table blog(
    blogId int primary key auto_increment,
    title varchar(1024),
    content mediumtext,
    userId int,
    postTime datetime
);

drop table if exists user;
create table user (
    userId int primary key auto_increment,
    username varchar(128) unique,
    password varchar(128)
);