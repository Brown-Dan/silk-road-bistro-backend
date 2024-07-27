create table takeaway_order
(
    organization_id text,
    item_name text[],
    address text,
    user_id text
);

create table reservation
(
    organization_id text,
    user_id text,
    time timestamp,
    peopleCount int
);

create table user_account
(
    organization_id text,
    username text,
    password        text,
    name text,
    email           text
);