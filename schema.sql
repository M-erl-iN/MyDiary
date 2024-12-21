drop table if exists users_notebooks_roles;
drop table if exists notes;
drop table if exists invites;
drop table if exists notebooks;
drop table if exists users;
drop table if exists roles;
drop table if exists invites;


create table roles
(
    id   numeric(2) primary key,
    name varchar(50) not null unique
);

insert into roles
values (1, 'creator'), -- просмотр, редактирование, добавление пользователей
       (2, 'user'),    -- просмотр, редактирование
       (3, 'spectator'); -- просмотр


create table users
(
    id            bigserial primary key,
    nickname      varchar(40)  not null,
    email         varchar(100) not null unique,
    phone         numeric(11) check ( phone >= 1000000000 ) unique,
    hash_password varchar(60)  not null,
    birthdate     date check ( birthdate > '1890-01-01' ),

    cookie_token  varchar(36) unique,
    image_id varchar(36) default '76fb957b-5a27-48dd-8613-f40711ce5f55'
);

create table notebooks
(
    id            bigserial primary key,
    title         varchar(60) not null,
    description   varchar(500),
    creation_date date default now(),
    creator_id    bigint references users (id)
);

create table notes
(
    id            bigserial primary key,
    title         varchar(60) not null,
    text          varchar(1750),
    creation_date date default now(),
    creator_id    bigint references users (id),
    notebook_id   bigint references notebooks (id)
);

create table users_notebooks_roles
(
    id          bigserial primary key,
    user_id     bigint references users (id),
    notebook_id bigint references notebooks (id),
    role_id     numeric(2) references roles (id),
    append_date date default now(),
    unique (user_id, notebook_id)
);

create table invites
(
    id             bigserial primary key,
    user_send_id   bigint references users (id),
    user_invite_id bigint references users (id),
    notebook_id    bigint references notebooks (id),
    role_id        bigint references roles (id),
    unique (user_invite_id, notebook_id)
);
