CREATE TABLE academico_campeonato (
	id_academico_campeonato INT AUTO_INCREMENT PRIMARY KEY,
	id_academico INT NOT NULL,
	id_campeonato INT NOT NULL,
	CONSTRAINT fk_academico_campeonato_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_academico_campeonato_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato)
);