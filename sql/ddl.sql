create schema if not exists spend_smart collate utf8mb4_unicode_ci;

create table if not exists category
(
    id binary(16) not null primary key,
    name varchar(25) not null,
    constraint category_id_uindex
        unique (id),
    constraint category_name_uindex
        unique (name)
);

create table if not exists custom_schedule
(
    id binary(16) not null primary key,
    name varchar(65) null,
    date_1 date not null,
    date_2 date not null,
    constraint custom_schedule_id_uindex
        unique (id)
);

create table if not exists funding_schedule_type
(
    id binary(16) not null primary key,
    type varchar(25) not null,
    constraint funding_schedule_type_id_uindex
        unique (id),
    constraint funding_schedule_type_type_uindex
        unique (type)
);

create table if not exists user
(
    id binary(16) not null primary key,
    email varchar(255) not null,
    email_verified boolean not null default false,
    first_name varchar(65) not null,
    last_name varchar(65) not null,
    age int null,
    phone_number varchar(50) null,
    provider varchar(50) not null,
    provider_id varchar(30) not null,
    image_url varchar(100),
    created_timestamp timestamp default CURRENT_TIMESTAMP not null,
    updated_timestamp timestamp default CURRENT_TIMESTAMP not null,
    constraint user_email_uindex
        unique (email),
    constraint user_id_uindex
        unique (id),
    constraint user_phone_number_uindex
        unique (phone_number)
);

create table if not exists expense_repeat_schedule_type
(
    id binary(16) not null primary key,
    type varchar(25) not null,
    constraint expense_repeat_schedule_type_id_uindex
        unique (id),
    constraint expense_repeat_schedule_type_type_uindex
        unique (type)
);

create table if not exists expense
(
    id binary(16) not null primary key,
    user_id binary(16) not null,
    expense_repeat_schedule_type_id binary(16) not null,
    funding_schedule_type_id binary(16) not null,
    name varchar(65) not null,
    repeat_date date not null,
    targeted_amount decimal(13,4) not null,
    saved_amount decimal(13,4) not null,
    fund_date date null,
    created_timestamp timestamp default CURRENT_TIMESTAMP not null,
    updated_timestamp timestamp default CURRENT_TIMESTAMP not null,
    constraint expense_id_uindex
        unique (id),
    constraint expense_expense_repeat_schedule_type_id_fk
        foreign key (expense_repeat_schedule_type_id) references expense_repeat_schedule_type (id)
            on update cascade,
    constraint expense_funding_schedule_type_id_fk
        foreign key (funding_schedule_type_id) references funding_schedule_type (id)
            on update cascade,
    constraint expense_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table if not exists expense_custom_schedule
(
    id binary(16) not null primary key,
    expense_id binary(16) not null,
    custom_schedule_id binary(16) not null,
    constraint expense_custom_schedule_expense_id_uindex
        unique (expense_id),
    constraint expense_custom_schedule_id_uindex
        unique (id),
    constraint expense_custom_schedule_custom_schedule_id_fk
        foreign key (custom_schedule_id) references custom_schedule (id)
            on update cascade on delete cascade,
    constraint expense_custom_schedule_expense_id_fk
        foreign key (expense_id) references expense (id)
            on update cascade on delete cascade
);

create table if not exists goal
(
    id binary(16) not null primary key,
    user_id binary(16) not null,
    funding_schedule_type_id binary(16) not null,
    name varchar(65) not null,
    targeted_date date not null,
    targeted_amount decimal(13,4) not null,
    saved_amount decimal(13,4) default 0.0000 not null,
    fund_date date null,
    created_timestamp timestamp default CURRENT_TIMESTAMP not null,
    updated_timestamp timestamp default CURRENT_TIMESTAMP not null,
    constraint goal_id_uindex
        unique (id),
    constraint goal_funding_schedule_type_id_fk
        foreign key (funding_schedule_type_id) references funding_schedule_type (id)
            on update cascade,
    constraint goal_user_id_fk
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table if not exists goal_custom_schedule
(
    id binary(16) not null primary key,
    goal_id binary(16) not null,
    custom_schedule_id binary(16) not null,
    constraint goal_custom_schedule_goal_id_uindex
        unique (goal_id),
    constraint goal_custom_schedule_id_uindex
        unique (id),
    constraint goal_custom_schedule_custom_schedule_id_fk
        foreign key (custom_schedule_id) references custom_schedule (id)
            on update cascade on delete cascade,
    constraint goal_custom_schedule_goal_id_fk
        foreign key (goal_id) references goal (id)
            on update cascade on delete cascade
);

create table if not exists subcategory
(
    id binary(16) not null primary key,
    category_id binary(16) not null,
    name varchar(65) not null,
    constraint subcategory_id_uindex
        unique (id),
    constraint subcategory_name_uindex
        unique (name),
    constraint subcategory_category_id_fk
        foreign key (category_id) references category (id)
            on update cascade
);

create table if not exists transaction_category
(
    id binary(16) not null
        primary key,
    transaction_id varchar(37) not null,
    category_id binary(16) not null,
    constraint transaction_category_transaction_id_uindex
        unique (transaction_id),
    constraint transaction_category_category_id_fk
        foreign key (category_id) references category (id)
            on update cascade on delete cascade
);

create table if not exists transaction_subcategory
(
    id binary(16) not null
        primary key,
    transaction_id varchar(37) not null,
    subcategory_id binary(16) not null,
    constraint transaction_subcategory_transaction_id_uindex
        unique (transaction_id),
    constraint transaction_subcategory_subcategory_id_fk
        foreign key (subcategory_id) references subcategory (id)
            on update cascade on delete cascade
);
