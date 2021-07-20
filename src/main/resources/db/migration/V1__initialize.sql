create table security_test.users (
    id          bigserial,
    login    varchar(32) not null unique,
    password    varchar(64) not null,
    scores      integer,
    primary key (id)
);

create table security_test.roles (
    id  serial,
    name    varchar(32) not null,
    primary key (id)
);

create table security_test.users_roles (
    users_id    bigint not null,
    roles_id    int not null,
    primary key (users_id, roles_id),
    foreign key (users_id) references users (id),
    foreign key (roles_id) references roles (id)
);