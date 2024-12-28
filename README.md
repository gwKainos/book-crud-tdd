# kr.kainos.book
TDD 테스트

```mysql
create table Book
(
    id     bigint auto_increment primary key,
    isbn   varchar(20) unique not null comment '도서번호',
    title  varchar(100)       not null comment '도서명',
    author varchar(50)        not null comment '저자',
    key idx_title (title),
    key idx_author (author)
);
```
H2 Console 접속
```text
Saved Settings:	Generic H2 (Server)
JDBC URL: jdbc:h2:mem:testdb
```

