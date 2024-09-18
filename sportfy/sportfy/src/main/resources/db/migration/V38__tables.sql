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
    id_modalidade_esportiva int NOT NULL,
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
    id_time1 int NOT NULL,
    id_time2 int NOT NULL,
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
    id_jogador int NOT NULL,
    nota int,
    id_academico_avaliador int NOT NULL,
    CONSTRAINT fk_academico_avaliador FOREIGN KEY (id_academico_avaliador) REFERENCES academico(id_academico),
    CONSTRAINT fk_avaliacao_jogador FOREIGN KEY (id_jogador) REFERENCES jogador(id_jogador)
);