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

CREATE TABLE usuario_canal (
	id_usuario_canal INT AUTO_INCREMENT PRIMARY KEY,
	id_canal INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_usuario_canal_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE publicacao (
	id_publicacao INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	descricao TEXT NOT NULL,
	data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_canal INT NOT NULL,
	id_usuario INT NOT NULL,
	id_modalidade_esportiva INT,
	CONSTRAINT fk_publicacao_canal FOREIGN KEY (id_canal) REFERENCES canal(id_canal),
	CONSTRAINT fk_publicacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
	CONSTRAINT fk_publicacao_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);

CREATE TABLE curtida_publicacao (
	id_curtida_publicacao INT AUTO_INCREMENT PRIMARY KEY,
	data_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_publicacao INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_curtida_publicacao_publicacao FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao),
	CONSTRAINT fk_curtida_publicacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE comentario (
	id_comentario INT AUTO_INCREMENT PRIMARY KEY,
	descricao TEXT NOT NULL,
	data_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_publicacao INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_comentario_publicacao FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao),
	CONSTRAINT fk_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE curtida_comentario (
	id_curtida_comentario INT AUTO_INCREMENT PRIMARY KEY,
	data_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_comentario INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_curtida_comentario_comentario FOREIGN KEY (id_comentario) REFERENCES comentario(id_comentario),
	CONSTRAINT fk_curtida_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

