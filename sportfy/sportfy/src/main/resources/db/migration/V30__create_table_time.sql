CREATE TABLE time (
	id_time INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	id_campeonato INT NOT NULL,
	CONSTRAINT fk_time_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato)
);