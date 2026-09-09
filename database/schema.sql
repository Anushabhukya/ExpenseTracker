create database expense_tracker;
use expense_tracker;

create table users (
    user_id int auto_increment primary key,
    name varchar(100),
    email varchar(100) unique,
    password varchar(100),
    created_at timestamp default current_timestamp
);

create table categories (
    category_id int auto_increment primary key,
    user_id int,
    category_name varchar(50),
    foreign key (user_id) references users(user_id)
);

create table income (
    income_id int auto_increment primary key,
    user_id int,
    amount decimal(10,2),
    source varchar(100),
    income_date date,
    foreign key (user_id) references users(user_id)
);

create table expenses (
    expense_id int auto_increment primary key,
    user_id int,
    category_id int,
    amount decimal(10,2),
    description varchar(255),
    expense_date date,
    foreign key (user_id) references users(user_id),
    foreign key (category_id) references categories(category_id)
);

create table budgets (
    budget_id int auto_increment primary key,
    user_id int,
    category_id int,
    budget_amount decimal(10,2),
    budget_month date,
    foreign key (user_id) references users(user_id),
    foreign key (category_id) references categories(category_id)
);