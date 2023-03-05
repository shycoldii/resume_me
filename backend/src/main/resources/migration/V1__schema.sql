create table users (
    id bigserial primary key not null,
    name varchar(40) not null,
    username varchar(15) not null,
    email varchar(40) not null,
    password varchar(100) not null,
    created_at timestamp,
    last_modified_at timestamp
    );

alter table users
    add constraint username_ukey
        unique (username);

alter table users
    add constraint email_ukey
        unique (username);

create table roles (
    id bigserial primary key not null,
    name varchar(60) not null
    );

alter table roles
    add constraint name_ukey
        unique (name);

create table users_to_roles (
    user_id  bigint not null,
    role_id  bigint not null
    );

alter table users_to_roles
    add constraint user_role_id_pk
        primary key (user_id, role_id);

alter table users_to_roles
    add constraint user_roles_role_id_fk
        foreign key (role_id) references roles (id);

alter table users_to_roles
    add constraint user_roles_user_id_fk
        foreign key (user_id) references users (id);
