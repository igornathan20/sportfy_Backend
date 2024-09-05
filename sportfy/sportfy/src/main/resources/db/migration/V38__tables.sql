CREATE TABLE endereco (
	id_endereco INT AUTO_INCREMENT PRIMARY KEY,
	cep VARCHAR(8) NOT NULL,
	uf CHAR(2) NOT NULL,
	cidade VARCHAR(50) NOT NULL,
	bairro VARCHAR(50) NOT NULL,
	rua VARCHAR(100) NOT NULL,
	numero VARCHAR(50) NOT NULL,
	complemento VARCHAR(50)
);

CREATE TABLE campeonato (
	id_campeonato INT AUTO_INCREMENT PRIMARY KEY,
	codigo VARCHAR(10) NOT NULL UNIQUE,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	aposta VARCHAR(100),
	data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	data_inicio TIMESTAMP NOT NULL,
	data_fim TIMESTAMP NOT NULL,
	limite_times INT NOT NULL,
	limite_participantes INT NOT NULL,
	ativo BOOLEAN DEFAULT TRUE,
	id_endereco INT NOT NULL,
	privacidade_campeonato VARCHAR(15) NOT NULL,
	id_academico INT NOT NULL,
	id_modalidade_esportiva INT NOT NULL,
	situacao_campeonato VARCHAR(15) NOT NULL,
	CONSTRAINT fk_campeonato_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco),
	CONSTRAINT fk_campeonato_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_campeonato_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);