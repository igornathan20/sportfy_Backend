CREATE TABLE jogador (
	id_jogador INT AUTO_INCREMENT PRIMARY KEY,
	pontuacao INT DEFAULT 0,
	melhor_jogador INT DEFAULT 0,
	id_academico INT NOT NULL,
	id_time_partida INT NOT NULL,
	CONSTRAINT fk_jogador_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_jogador_time_partida FOREIGN KEY (id_time_partida) REFERENCES time_partida(id_time_partida)
);