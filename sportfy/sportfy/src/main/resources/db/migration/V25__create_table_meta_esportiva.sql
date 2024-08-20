CREATE TABLE meta_esportiva (
	id_meta_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	foto VARCHAR(255),
	id_modalidade_esportiva INT NOT NULL,
	CONSTRAINT fk_meta_esportiva_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);