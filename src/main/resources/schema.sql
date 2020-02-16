DROP TABLE IF EXISTS waypoint;
DROP TABLE IF EXISTS hero;

create table Hero(
    name varchar(255) primary key unique,
    house varchar(255)
);

create table Waypoint(
    point_id serial primary key,
    x real,
    y real,
    velocity int,
    delaymillis int,
    waypoint_hero varchar(255) references Hero(name) ON DELETE CASCADE null
);

