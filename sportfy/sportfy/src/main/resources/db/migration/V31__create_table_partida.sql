CREATE TABLE partida (
	id_partida INT AUTO_INCREMENT PRIMARY KEY,
	data_partida TIMESTAMP NOT NULL,
	id_campeonato INT NOT NULL,
	CONSTRAINT fk_partida_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato)
);