create table budget(
    id bigint not null primary key auto_increment,
    budget_month date not null,
    user_id bigint not null,
    created_at datetime not null default now(),
    updated_at datetime not null default now()
);

alter table budget add foreign key(user_id) references users(id);