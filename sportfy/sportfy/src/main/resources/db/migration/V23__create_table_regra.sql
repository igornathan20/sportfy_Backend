CREATE TABLE regra (
	id_regra INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	id_modalidade_esportiva INT NOT NULL,
	CONSTRAINT fk_regra_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);