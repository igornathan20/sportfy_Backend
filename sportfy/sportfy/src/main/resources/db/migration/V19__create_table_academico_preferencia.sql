CREATE TABLE academico_preferencia (
	id_academico_preferencia INT AUTO_INCREMENT PRIMARY KEY,
	id_preferencia INT NOT NULL,
	id_academico INT NOT NULL,
	CONSTRAINT fk_academico_preferencia_preferencia FOREIGN KEY (id_preferencia) REFERENCES preferencia(id_preferencia),
	CONSTRAINT fk_academico_preferencia_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);