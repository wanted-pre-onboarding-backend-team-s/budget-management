# 예산 테이블
create table budget
(
    id          int    not null primary key auto_increment,
    amount      bigint not null default 0,
    category_id int    not null,
    budget_month date not null,
    created_at  datetime not null default now(),
    updated_at  datetime not null default now()
)