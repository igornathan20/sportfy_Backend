CREATE TABLE conquista (
	id_conquista INT AUTO_INCREMENT PRIMARY KEY,
	data_conquista TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_academico INT NOT NULL,
	id_meta_esportiva INT NOT NULL,
	CONSTRAINT fk_conquista_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_conquista_meta_esportiva FOREIGN KEY (id_meta_esportiva) REFERENCES meta_esportiva(id_meta_esportiva)
);