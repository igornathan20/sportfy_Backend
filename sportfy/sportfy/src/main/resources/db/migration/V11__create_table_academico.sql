CREATE TABLE academico (
	id_academico INT AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(100) NOT NULL UNIQUE,
	curso VARCHAR(50),
	id_usuario INT NOT NULL,
	CONSTRAINT fk_academico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);