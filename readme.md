# Project

Optimizing Data Access in Spring Boot 3 with HAProxy and MySQL: Achieving High Availability and Scalability through Separate Read and Write Operations

"Imagine a system similar to Twitter, where reading is far more common than writing (95% read, 5% write). To handle this, we've set up a smart system using MySQL. We split the workload by having one main hub (the master) that takes care of writing stuff, while several other parts (the slaves) handle all the reading requests efficiently.

Today, we will talk about this with code, exploring how the system is designed to manage Twitter-like activity smoothly. Check out the system diagram to see how everything connects. This way, we make sure the system not only copes with the demands but also runs efficiently, handling all those read operations with ease."

## Read this article on Medium

[Medium Article](https://medium.com/@htyesilyurt/optimizing-data-access-in-spring-boot-3-with-haproxy-and-mysql-1-master-2-slave-separate-read-25fced42f761)

## System Diagram

<img src="https://github.com/tugayesilyurt/springboot3-haproxy-mysql-1master-2slave-read-write-separate/blob/main/assets/systemdiagram.png" width=60% height=60%>

## Tech Stack

- Java 21
- Spring Boot 3
- HAProxy
- Mysql (master - slave replication)
- Docker

## Installation

 - follow the steps:

 - first create network

```shell
   docker network create mysql-master-slave
```

 - run master mysql

```shell
   docker run -d -p 3307:3306 --net=mysql-master-slave --hostname=master --name mysql-master -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=twitter -e MYSQL_USER=twitter -e MYSQL_PASSWORD=123456 -v ./master-data:/.          var/lib/mysql mysql/mysql-server:latest --server-id=1 --log-bin='mysql-bin-1.log' --relay_log_info_repository=TABLE --master-info-repository=TABLE --gtid-mode=on --enforce-gtid-consistency
```
 - docker exec -it master_mysql_id sh - mysql -p -u root - password:123456

```shell
 CREATE USER 'master_user'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
 GRANT REPLICATION SLAVE ON *.* TO 'master_user'@'%';
```

 - run slave1 mysql

```shell
   docker run -d -p 3308:3306 --net=mysql-master-slave --hostname=slave1 --name mysql-slave1 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=twitter -e MYSQL_USER=twitter -e MYSQL_PASSWORD=123456 -v ./slave1-data:/var/lib/mysql mysql/mysql-server:latest --server-id=2  --relay_log_info_repository=TABLE --master-info-repository=TABLE --gtid-mode=on --enforce-gtid-consistency
```

 - docker exec -it slave1_mysql_id sh - mysql -p -u root - password:123456

```shell
   CHANGE MASTER TO MASTER_HOST='master', MASTER_USER='master_user', MASTER_PASSWORD='123456', MASTER_AUTO_POSITION = 1 FOR CHANNEL 'master';
   START SLAVE;
   SHOW SLAVE STATUS\G;
```

 - run slave2 mysql

```shell
   docker run -d -p 3309:3306 --net=mysql-master-slave --hostname=slave2 --name mysql-slave2 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=twitter -e MYSQL_USER=twitter -e MYSQL_PASSWORD=123456 -v ./slave2-data:/var/lib/mysql mysql/mysql-server:latest --server-id=3  --relay_log_info_repository=TABLE --master-info-repository=TABLE --gtid-mode=on --enforce-gtid-consistency
```

 - docker exec -it slave2_mysql_id sh - mysql -p -u root - password:123456

```shell
   CHANGE MASTER TO MASTER_HOST='master', MASTER_USER='master_user', MASTER_PASSWORD='123456', MASTER_AUTO_POSITION = 1 FOR CHANNEL 'master';
   START SLAVE;
   SHOW SLAVE STATUS\G;
```

 - now mysql master-slave replication is okey

 - we will create user for haproxy check

 - docker exec -it master_mysql_id sh - mysql -p -u root - password:123456

```shell
   create user 'haproxy_check'@'%';
   update user set plugin='mysql_native_password' where user='haproxy_check';
   select user from mysql.user;
```

 - now we can run haproxy!

```shell
   docker run -d --name haproxy_mysql -v ./haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg --net=mysql-master-slave -p 8080:8080 -p 3306:3306 haproxy:latest
```

 - and we are ready!

 - separate mysql master - slave application.yml

- `for master application.yml`

```
server:
  port: 8090

spring:
  datasource:
      url: jdbc:mysql://localhost:3307/twitter  # master mysql
      username: twitter
      password: 123456
      driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
```

- `for slave application.yml`

```
server:
  port: 8091

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/twitter  # haproxy -> slave1 mysql and slave2 mysql
    username: twitter
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update

```
## HAProxy UI

<img src="https://github.com/tugayesilyurt/springboot3-haproxy-mysql-1master-2slave-read-write-separate/blob/main/assets/haproxy.png" width=65% height=65%>









