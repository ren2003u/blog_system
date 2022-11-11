create database if not exist blog_system;

use blog_system;

drop table if exist blog;
create table blog(
    blogId int primary key auto_increment,
    title varchar(1024),
    content mediumtext,
    userId int,
    postTime datetime,
)

drop table if exist user;
create table user(
    userId int primary key auto_increment,
    username varchar(1024) unique,
    password varchar(128),
)