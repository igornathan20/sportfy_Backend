CREATE TABLE partida (
	id_partida INT AUTO_INCREMENT PRIMARY KEY,
	data_partida TIMESTAMP NOT NULL,
	id_campeonato INT NOT NULL,
	id_fase_partida INT NOT NULL,
	CONSTRAINT fk_partida_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato),
	CONSTRAINT fk_partida_fase_partida FOREIGN KEY (id_fase_partida) REFERENCES fase_partida(id_fase_partida)
);