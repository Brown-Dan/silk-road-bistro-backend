create table opening_hours
(
    id            text primary key,
    opening_hours text
);

create table homepage
(
    id        text primary key,
    biography text,
    address   text,
    images    text[]
);

create table article
(
    homepage_id text,
    title   text,
    content text,
    date    Date
);

create table offer (
    homepage_id text,
    title text,
    content text
)