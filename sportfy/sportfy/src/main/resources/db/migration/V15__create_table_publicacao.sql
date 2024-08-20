CREATE TABLE publicacao (
	id_publicacao INT AUTO_INCREMENT PRIMARY KEY,
	descricao TEXT NOT NULL,
	data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_canal INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_publicacao_canal FOREIGN KEY (id_canal) REFERENCES canal(id_canal),
	CONSTRAINT fk_publicacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);