create table users
(
    id    serial                not null,
    username       varchar(100)          not null,
    email          varchar(100)          not null,
    password       text                  not null,
    is_super_admin boolean default false not null,
    is_active      boolean default true  not null,
    constraint users_pk
        primary key (id),
    constraint users_pk_2
        unique (username),
    constraint users_pk_3
        unique (email)
);


create table projects
(
    uuid          uuid         default gen_random_uuid()  not null,
    nome_progetto varchar(200)                            not null,
    descrizione   text                                    not null,
    stato         varchar(100)                            not null,
    created_at    date         default date(now())        not null,
    updated_at    date,
    ended_at      date,
    constraint project_pk_2
        primary key (uuid),
    constraint project_pk
        unique (nome_progetto)
);


create table project_roles
(
    id         serial       not null,
    nome_ruolo varchar(200) not null,
    constraint project_roles_pk
        primary key (id)
);


create table project_member
(
    project_id      uuid   not null,
    user_id         serial not null,
    project_role_id integer,
    constraint project_member_pk
        primary key (user_id, project_role_id, project_id),
    constraint project_member_project_roles_id_fk
        foreign key (project_role_id) references project_roles,
    constraint project_member_projects_uuid_fk
        foreign key (project_id) references projects,
    constraint project_member_users_id_fk
        foreign key (user_id) references users (id)
);

comment on table project_member is 'Tabella di join per appertenenza ad un progetto e con che ruolo';


create table tasks
(
    id             serial            not null,
    titolo         varchar(200)      not null,
    descrizione    text              not null,
    stato          varchar(100)      not null,
    priorita       integer default 1 not null,
    project_id     uuid              not null,
    assignee_id    integer           not null,
    reporter_id    integer           not null,
    parent_task_id integer,
    constraint tasks_pk
        primary key (id),
    constraint tasks_projects_uuid_fk
        foreign key (project_id) references projects,
    constraint tasks_tasks_id_fk
        foreign key (parent_task_id) references tasks,
    constraint tasks_users_id_fk
        foreign key (assignee_id) references users,
    constraint tasks_users_id_fk_2
        foreign key (reporter_id) references users
);


create table comments
(
    id         serial                   not null,
    testo      text                     not null,
    created_at date default date(now()) not null,
    task_id    integer,
    user_id    integer,
    constraint comments_pk
        primary key (id),
    constraint comments_tasks_id_fk
        foreign key (task_id) references tasks,
    constraint comments_users_id_fk
        foreign key (user_id) references users
);

