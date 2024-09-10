CREATE TABLE canal (
	id_canal INT AUTO_INCREMENT PRIMARY KEY,
	tipo_canal VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE fase_partida (
	id_fase_partida INT AUTO_INCREMENT PRIMARY KEY,
	tipo_fase_partida VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE permissao (
	id_permissao INT AUTO_INCREMENT PRIMARY KEY,
	tipo_permissao VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuario (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	telefone VARCHAR(11),
	genero VARCHAR(11),
	data_nascimento DATE,
	foto VARCHAR(255),
	data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	ativo BOOLEAN DEFAULT TRUE,
	id_permissao INT NOT NULL,
	CONSTRAINT fk_usuario_permissao FOREIGN KEY (id_permissao) REFERENCES permissao(id_permissao)
);

CREATE TABLE academico (
	id_academico INT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(100) NOT NULL UNIQUE,
	curso VARCHAR(50),
	id_usuario INT NOT NULL,
	CONSTRAINT fk_academico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE notificacao (
	id_notificacao INT AUTO_INCREMENT PRIMARY KEY,
    id_academico INT NOT NULL,
    modalidade_esportivas BOOLEAN NOT NULL,
    campeonatos BOOLEAN NOT NULL,
    CONSTRAINT fk_academico_notificacao FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);

CREATE TABLE privacidade (
    id_privacidade INT AUTO_INCREMENT PRIMARY KEY,
    id_academico INT NOT NULL,
    mostrar_modalidades_esportivas BOOLEAN NOT NULL,
    mostrar_historico_campeonatos BOOLEAN NOT NULL,
    mostrar_estatisticas_modalidades_esportivas BOOLEAN NOT NULL,
    mostrar_conquistas BOOLEAN NOT NULL,
    CONSTRAINT fk_academico_privacidade FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);

CREATE TABLE modalidade_esportiva (
	id_modalidade_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	foto VARCHAR(255),
	data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE academico_modalidade_esportiva (
	id_academico_modalidade_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	id_academico INT NOT NULL,
	id_modalidade_esportiva INT NOT NULL,
	CONSTRAINT fk_academico_modalidade_esportiva_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_academico_modalidade_esportiva_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);