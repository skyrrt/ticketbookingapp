insert into movie (movie_name, directors_name) values
('boże ciało', 'komasa'),
('chłopaki nie płączą', 'lubaszenko'),
('szóst zmysł', 'shyamalan');

insert into room (room_name, row_quantity, seats_in_row) values
('ążźć', 5,7),
('sala2',6,7),
('sala3', 8,9);

insert into screening(screening_date, room_id, movie_id) values
('2020-01-20T14:08:23.445898400+01:00',1,2),
('2020-01-20T15:08:23.445898400+01:00',2,3),
('2020-01-25T14:08:23.445898400+01:00',1,1),
('2020-01-20T16:08:23.445898400+01:00',3,1),
('2020-01-18T14:08:23.445898400+01:00',2,3),
('2020-01-20T17:08:23.445898400+01:00',3,2);

insert into booking (screening_id) values
(1);

insert into ticket (row_num, seat, price, booking_id) values
(1,1,10,1),
(3,3,10,1);