create user 'regadmin'@'%' identified by 'admin_root_reg';
grant all on *.* to 'regadmin'@'%' with grant option;
create database citizens;
connect citizens;
create table citizen(id VARCHAR(8) PRIMARY KEY, firstname VARCHAR(30) NOT NULL, lastname VARCHAR(30) NOT NULL, 
gender VARCHAR(20) NOT NULL, birthdate VARCHAR(10) NOT NULL, taxnumber VARCHAR(9), address VARCHAR(50));
exit