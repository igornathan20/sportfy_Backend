CREATE TABLE comentario (
	id_comentario INT AUTO_INCREMENT PRIMARY KEY,
	descricao TEXT NOT NULL,
	data_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_publicacao INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_comentario_publicacao FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao),
	CONSTRAINT fk_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);