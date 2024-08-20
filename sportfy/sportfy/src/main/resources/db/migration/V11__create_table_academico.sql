CREATE TABLE academico (
	id_academico INT AUTO_INCREMENT PRIMARY KEY,
	curso VARCHAR(50),
	id_usuario INT NOT NULL,
	CONSTRAINT fk_academico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);