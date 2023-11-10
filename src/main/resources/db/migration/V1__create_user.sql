create table users
(
    id         bigint       not null primary key auto_increment,
    username   varchar(255) not null unique,
    password   varchar(255) not null,
    is_deleted tinyint      not null default 0,
    created_at datetime     not null default now(),
    updated_at datetime     not null default now(),
    deleted_at datetime     not null default now()
);
