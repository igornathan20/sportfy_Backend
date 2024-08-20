CREATE TABLE administrador (
	id_administrador INT AUTO_INCREMENT PRIMARY KEY,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_administrador_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);