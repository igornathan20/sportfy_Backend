CREATE TABLE academico_modalidade_esportiva (
	id_academico_modalidade_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	id_academico INT NOT NULL,
	id_modalidade_esportiva INT NOT NULL,
	CONSTRAINT fk_academico_modalidade_esportiva_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_academico_modalidade_esportiva_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);