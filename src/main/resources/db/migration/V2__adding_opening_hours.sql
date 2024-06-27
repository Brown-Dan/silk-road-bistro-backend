create table open_close_time(
  opening_time time,
  closing_time time,
  closed bool
);

create table opening_hours(
    monday open_close_time,
    tuesday open_close_time,
    wednesday open_close_time,
    thursday open_close_time,
    friday open_close_time,
    saturday open_close_time,
    sunday open_close_time
)