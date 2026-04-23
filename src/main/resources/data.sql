--Datos de Category
INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

--Datos de Author
INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

--Datos de Game
INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

--Datos de Client
INSERT INTO client(name) VALUES ('Paula');
INSERT INTO client(name) VALUES ('Marco');
INSERT INTO client(name) VALUES ('Sergio');
INSERT INTO client(name) VALUES ('Esteban');
INSERT INTO client(name) VALUES ('Pedro');
INSERT INTO client(name) VALUES ('Paco');

--Datos de Prestamo
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-10', '2026-04-17', 1, 1); --Paula, On Mars
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-11', '2026-04-18', 2, 2); --Marco, Aventureros al tren
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-12', '2026-04-19', 4, 3); --Sergio, Barrage
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-15', '2026-04-22', 6, 4); --Esteban, Azul
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-18', '2026-04-25', 3, 5); --Pedro, 1920: Wall Street
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-20', '2026-04-27', 5, 6); --Paco, Marco Polo
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-21', '2026-04-28', 1, 2); --Marco, On Mars
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-23', '2026-04-30', 2, 4); --Esteban, Aventureros al tren
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-25', '2026-05-02', 4, 1); --Paula, Barrage
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-27', '2026-05-04', 6, 3); --Sergio, Azul
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-28', '2026-05-05', 3, 6); --Paco, 1920: Wall Street
INSERT INTO prestamo(fecha_prestamo, fecha_devolucion, game_id, client_id) VALUES ('2026-04-30', '2026-05-07', 5, 5); --Pedro, Marco Polo