create table skills
(
    id   bigint       not null
        primary key,
    name varchar(255) null,
    constraint UK_SKILLS_NAME
        unique (name)
);

CREATE SEQUENCE skills_seq INCREMENT BY 50 START WITH 1;
CREATE SEQUENCE social_links_seq INCREMENT BY 50 START WITH 1;

create table teams
(
    id   bigint       not null
        primary key,
    name varchar(255) null,
    constraint UK_TEAMS_NAME
        unique (name)
);

CREATE SEQUENCE teams_seq INCREMENT BY 50 START WITH 1;

create table users
(
    id          bigint        not null
        primary key,
    description varchar(255)  null,
    email       varchar(255)  null,
    image_url   varchar(2048) null,
    name        varchar(255)  null,
    fk_team_id  bigint        null,
    constraint UQ_USERS_EMAIL
        unique (email),
    constraint FK_USERS_TEAMS_ID
        foreign key (fk_team_id) references teams (id)
);

create table social_links
(
    id         bigint        not null
        primary key,
    url        varchar(2048) null,
    fk_user_id bigint        null,
    constraint FK_SOCIAL_USERS_ID
        foreign key (fk_user_id) references users (id)
);

CREATE SEQUENCE users_seq INCREMENT BY 50 START WITH 1;

create table users_skills
(
    user_id  bigint not null,
    skill_id bigint not null,
    primary key (user_id, skill_id),
    constraint FK_US_SKILLS_ID
        foreign key (skill_id) references skills (id),
    constraint FK_US_USERS_ID
        foreign key (user_id) references users (id)
);

