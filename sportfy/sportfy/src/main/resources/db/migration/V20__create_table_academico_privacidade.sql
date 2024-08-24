CREATE TABLE academico_privacidade (
	id_academico_privacidade INT AUTO_INCREMENT PRIMARY KEY,
	id_privacidade INT NOT NULL,
	id_academico INT NOT NULL,
	CONSTRAINT fk_academico_privacidade_privacidade FOREIGN KEY (id_privacidade) REFERENCES privacidade(id_privacidade),
	CONSTRAINT fk_academico_privacidade_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);