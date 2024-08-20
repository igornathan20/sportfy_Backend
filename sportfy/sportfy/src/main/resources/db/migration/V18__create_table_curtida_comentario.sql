CREATE TABLE curtida_comentario (
	id_curtida_comentario INT AUTO_INCREMENT PRIMARY KEY,
	data_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_comentario INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_curtida_comentario_comentario FOREIGN KEY (id_comentario) REFERENCES comentario(id_comentario),
	CONSTRAINT fk_curtida_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);