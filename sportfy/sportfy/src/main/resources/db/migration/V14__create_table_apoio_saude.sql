CREATE TABLE apoio_saude (
	id_apoio_saude INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(100),
	telefone VARCHAR(11),
	descricao TEXT NOT NULL,
	data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_administrador INT NOT NULL,
	CONSTRAINT fk_apoio_saude_administrador FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador)
);