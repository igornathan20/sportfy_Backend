CREATE TABLE usuario_canal (
	id_usuario_canal INT AUTO_INCREMENT PRIMARY KEY,
	id_canal INT NOT NULL,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_usuario_canal_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);