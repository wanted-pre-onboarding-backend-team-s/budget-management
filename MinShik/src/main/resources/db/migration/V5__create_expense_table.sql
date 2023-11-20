create table EXPENSE(
    ID bigint not null primary key auto_increment,
    MEMO mediumtext,
    AMOUNT int not null,
    USER_ID bigint not null,
    CATEGORY_ID int not null ,
    EXPENSE_AT datetime not null,
    CREATED_AT datetime not null default now(),
    UPDATED_AT datetime not null default now()
);

alter table EXPENSE add constraint FK_expense_user_id foreign key (user_id) references users(id);
alter table EXPENSE add constraint FK_expense_category_id foreign key (category_id) references category(id);