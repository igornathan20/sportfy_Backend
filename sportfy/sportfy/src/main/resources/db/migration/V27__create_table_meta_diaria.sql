CREATE TABLE meta_diaria (
	id_meta_diaria INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	objetivo VARCHAR(50) NOT NULL,
	quantidade_concluido INT DEFAULT 0,
	progresso_atual INT DEFAULT 0,
	progresso_maximo INT NOT NULL,
	progresso_item VARCHAR(30) NOT NULL,
	id_academico INT NOT NULL,
	id_situacao_meta_diaria INT NOT NULL,
	CONSTRAINT fk_meta_diaria_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_meta_diaria_situacao_meta_diaria FOREIGN KEY (id_situacao_meta_diaria) REFERENCES situacao_meta_diaria(id_situacao_meta_diaria)
);