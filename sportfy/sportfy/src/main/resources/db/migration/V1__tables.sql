CREATE TABLE canal (
	id_canal INT AUTO_INCREMENT PRIMARY KEY,
	tipo_canal VARCHAR(100) NOT NULL UNIQUE
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
	permissao INT NOT NULL
);

CREATE TABLE academico (
	id_academico INT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(100) NOT NULL UNIQUE,
	curso VARCHAR(100),
	id_usuario INT NOT NULL,
	CONSTRAINT fk_academico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE privacidade (
    id_privacidade INT AUTO_INCREMENT PRIMARY KEY,
    id_academico INT NOT NULL,
    mostrar_historico_campeonatos BOOLEAN NOT NULL,
    mostrar_estatisticas_modalidades_esportivas BOOLEAN NOT NULL,
    mostrar_conquistas BOOLEAN NOT NULL,
    CONSTRAINT fk_academico_privacidade FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);

CREATE TABLE modalidade_esportiva (
	id_modalidade_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL UNIQUE,
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

CREATE TABLE meta_esportiva (
	id_meta_esportiva INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	progresso_maximo INT NOT NULL,
	progresso_item VARCHAR(30) NOT NULL,
	foto VARCHAR(255),
	ativo BOOLEAN DEFAULT TRUE,
	id_modalidade_esportiva INT NOT NULL,
	CONSTRAINT fk_meta_esportiva_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);

CREATE TABLE conquista (
	id_conquista INT AUTO_INCREMENT PRIMARY KEY,
	progresso_atual INT DEFAULT 0,
	data_conquista TIMESTAMP,
	conquistado BOOLEAN DEFAULT FALSE,
	ativo BOOLEAN DEFAULT TRUE,
	id_academico INT NOT NULL,
	id_meta_esportiva INT NOT NULL,
	CONSTRAINT fk_conquista_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
	CONSTRAINT fk_conquista_meta_esportiva FOREIGN KEY (id_meta_esportiva) REFERENCES meta_esportiva(id_meta_esportiva)
);

CREATE TABLE administrador (
	id_administrador INT AUTO_INCREMENT PRIMARY KEY,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_administrador_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE meta_diaria (
	id_meta_diaria INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	objetivo VARCHAR(50),
	progresso_atual INT DEFAULT 0,
	progresso_maximo INT,
	progresso_item VARCHAR(30),
	id_academico INT NOT NULL,
	tipo_situacao_meta_diaria VARCHAR(50) NOT NULL,
	CONSTRAINT fk_meta_diaria_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);

CREATE TABLE apoio_saude (
	id_apoio_saude INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(100),
	telefone VARCHAR(11),
	descricao TEXT NOT NULL,
	data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_administrador INT NOT NULL,
	ativo BOOLEAN DEFAULT TRUE,
	CONSTRAINT fk_apoio_saude_administrador FOREIGN KEY (id_administrador) REFERENCES administrador(id_administrador)
);

CREATE TABLE usuario_canal (
	id_usuario_canal INT AUTO_INCREMENT PRIMARY KEY,
	id_canal INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_usuario_canal_canal FOREIGN KEY (id_canal) REFERENCES canal(id_canal),
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
    id_campeonato int AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    aposta VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    limite_times INT NOT NULL,
    limite_participantes INT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    id_endereco int NOT NULL,
    privacidade_campeonato VARCHAR(50) NOT NULL,
    id_academico int NOT NULL,
    id_modalidade_esportiva int NOT NULL,
    situacao_campeonato VARCHAR(50) NOT NULL,
    fase_atual VARCHAR(50),

    CONSTRAINT fk_campeonato_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco),
    CONSTRAINT fk_campeonato_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico),
    CONSTRAINT fk_campeonato_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva)
);

CREATE TABLE time (
    id_time int AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_campeonato int NOT NULL,
    CONSTRAINT fk_time_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato)
);

CREATE TABLE jogador (
    id_jogador int AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30),
    id_modalidade_esportiva int NOT NULL,
    situacao_jogador int NOT NULL,
    pontuacao INT,
    id_time int NOT NULL,
    id_academico int NOT NULL,
    CONSTRAINT fk_jogador_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva),
    CONSTRAINT fk_jogador_time FOREIGN KEY (id_time) REFERENCES time(id_time),
    CONSTRAINT fk_jogador_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);

CREATE TABLE resultado (
    id_resultado int AUTO_INCREMENT PRIMARY KEY,
    id_time int,
    pontuacao_time1 INT,
    pontuacao_time2 INT,
    descricao VARCHAR(255),
    CONSTRAINT fk_resultado_time FOREIGN KEY (id_time) REFERENCES time(id_time)
);

CREATE TABLE partida (
    id_partida int AUTO_INCREMENT PRIMARY KEY,
    data_partida TIMESTAMP,
    id_campeonato int NOT NULL,
    fase_partida VARCHAR(50),
    id_time1 int,
    id_time2 int,
    situacao_partida VARCHAR(50) NOT NULL,
    id_resultado int,
    CONSTRAINT fk_partida_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato),
    CONSTRAINT fk_partida_time1 FOREIGN KEY (id_time1) REFERENCES time(id_time),
    CONSTRAINT fk_partida_time2 FOREIGN KEY (id_time2) REFERENCES time(id_time),
    CONSTRAINT fk_partida_resultado FOREIGN KEY (id_resultado) REFERENCES resultado(id_resultado)
);

CREATE TABLE campeonato_partida (
    id_campeonato_partida INT AUTO_INCREMENT PRIMARY KEY,
    id_campeonato int NOT NULL,
    id_partida int NOT NULL,
    CONSTRAINT fk_campeonato_partida_campeonato FOREIGN KEY (id_campeonato) REFERENCES campeonato(id_campeonato),
    CONSTRAINT fk_campeonato_partida_partida FOREIGN KEY (id_partida) REFERENCES partida(id_partida)
);
CREATE TABLE avaliacao (
    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    id_academico_avaliado INT NOT NULL,
    id_modalidade_esportiva INT NOT NULL,
    id_academico_avaliador INT NOT NULL,
    nota INT NOT NULL,
    CONSTRAINT fk_academico_avaliado FOREIGN KEY (id_academico_avaliado) REFERENCES academico(id_academico),
    CONSTRAINT fk_modalidade_esportiva FOREIGN KEY (id_modalidade_esportiva) REFERENCES modalidade_esportiva(id_modalidade_esportiva),
    CONSTRAINT fk_academico_avaliador FOREIGN KEY (id_academico_avaliador) REFERENCES academico(id_academico)
);

CREATE TABLE curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);
