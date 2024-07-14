ALTER TABLE offer
    ADD COLUMN if not exists enabled boolean;


create table organization_account
(
    organization_id text,
    password        text,
    email           text
);