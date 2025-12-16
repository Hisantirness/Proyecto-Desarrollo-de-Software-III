-- Limpiar tablas si es necesario (Orden por FKs)
DELETE FROM barbero_especialidades;
-- DELETE FROM citas; -- ESTA TABLA NO EXISTE EN db_catalog (Pertenece a booking-service)
DELETE FROM barberos;
DELETE FROM especialidades;
DELETE FROM servicios; -- Ahora sí podemos borrar servicios

-- Reiniciar IDs (MySQL)
ALTER TABLE barberos AUTO_INCREMENT = 1;
ALTER TABLE especialidades AUTO_INCREMENT = 1;
ALTER TABLE servicios AUTO_INCREMENT = 1;

-- 1. Insertar Barberos (Los 4 Fantásticos)
INSERT INTO barberos (nombre, experiencia, foto_url) VALUES ('Santiago Villa', 'Master Barber - 10 años', 'https://randomuser.me/api/portraits/men/1.jpg');
INSERT INTO barberos (nombre, experiencia, foto_url) VALUES ('Edgar Rueda', 'Especialista en Barba', 'https://randomuser.me/api/portraits/men/2.jpg');
INSERT INTO barberos (nombre, experiencia, foto_url) VALUES ('Manuel Serna', 'Degradados Perfectos', 'https://randomuser.me/api/portraits/men/3.jpg');
INSERT INTO barberos (nombre, experiencia, foto_url) VALUES ('Adrian Velasquez', 'Estilo Clásico', 'https://randomuser.me/api/portraits/men/4.jpg');

-- 2. Insertar Especialidades
INSERT INTO especialidades (nombre) VALUES ('Corte Clásico con Tijera');
INSERT INTO especialidades (nombre) VALUES ('Navaja y Toalla Caliente');
INSERT INTO especialidades (nombre) VALUES ('Colorimetría Capilar');
INSERT INTO especialidades (nombre) VALUES ('Diseño de Barba');

-- 3. Relacionar Barberos con Especialidades (IDs asumiendo autoincrement 1..4)
-- Santiago (1) sabe todo
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (1, 1);
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (1, 2);
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (1, 4);

-- Edgar (2) es el rey de la navaja
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (2, 2);
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (2, 4);

-- Manuel (3) degradados y color
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (3, 1);
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (3, 3);

-- Adrian (4) clásico
INSERT INTO barbero_especialidades (barbero_id, especialidad_id) VALUES (4, 1);

-- 4. Insertar Servicios (Para que salgan en el menú)
INSERT INTO servicios (nombre, precio, duracion_minutos) VALUES ('Corte de Cabello Premium', 25.00, 45);
INSERT INTO servicios (nombre, precio, duracion_minutos) VALUES ('Afeitado de Lujo', 15.00, 30);
INSERT INTO servicios (nombre, precio, duracion_minutos) VALUES ('Combo Corte + Barba', 35.00, 60);
INSERT INTO servicios (nombre, precio, duracion_minutos) VALUES ('Perfilado de Cejas', 8.00, 15);
