CREATE TABLE time_partida (
	id_time_partida INT AUTO_INCREMENT PRIMARY KEY,
	pontuacao INT DEFAULT 0,
	id_time INT NOT NULL,
	id_partida INT NOT NULL,
	id_resultado_time_partida INT NOT NULL,
	CONSTRAINT fk_time_partida_time FOREIGN KEY (id_time) REFERENCES time(id_time),
	CONSTRAINT fk_time_partida_partida FOREIGN KEY (id_partida) REFERENCES partida(id_partida),
	CONSTRAINT fk_time_partida_resultado_time_partida FOREIGN KEY (id_resultado_time_partida) REFERENCES resultado_time_partida(id_resultado_time_partida)
);