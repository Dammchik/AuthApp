create table if not exists stations (
    id bigint primary key not null,
    station character varying(255) not null
);

insert into stations(id, station)
values
    (1, 'station-1'),
    (2, 'station-2'),
    (3, 'station-3'),
    (4, 'station-4'),
    (5, 'station-5'),
    (6, 'station-6'),
    (7, 'station-7'),
    (8, 'station-8'),
    (9, 'station-9'),
    (10, 'station-10');
