create table category
(
    id         int          not null primary key auto_increment,
    name       varchar(255) not null,
    created_at datetime     not null default now(),
    updated_at datetime     not null default now()
)