CREATE TABLE usuario (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	telefone VARCHAR(11),
	data_nascimento DATE,
	foto VARCHAR(255),
	data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	ativo BOOLEAN DEFAULT TRUE,
	id_permissao INT NOT NULL,
	CONSTRAINT fk_usuario_permissao FOREIGN KEY (id_permissao) REFERENCES permissao(id_permissao)
);