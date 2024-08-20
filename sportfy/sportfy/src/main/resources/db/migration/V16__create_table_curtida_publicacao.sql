CREATE TABLE curtida_publicacao (
	id_curtida_publicacao INT AUTO_INCREMENT PRIMARY KEY,
	data_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_publicacao INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_curtida_publicacao_publicacao FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao),
	CONSTRAINT fk_curtida_publicacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);