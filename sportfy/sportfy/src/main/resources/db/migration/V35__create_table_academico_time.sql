CREATE TABLE academico_time (
	id_academico_time INT AUTO_INCREMENT PRIMARY KEY,
	id_academico INT NOT NULL,
	id_time INT NOT NULL,
	CONSTRAINT fk_academico_time_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_academico_time_time FOREIGN KEY (id_time) REFERENCES time(id_time)
);