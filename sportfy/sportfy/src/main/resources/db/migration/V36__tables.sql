CREATE TABLE administrador (
	id_administrador INT AUTO_INCREMENT PRIMARY KEY,
	id_usuario INT NOT NULL,
	CONSTRAINT fk_administrador_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE meta_diaria (
	id_meta_diaria INT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(50) NOT NULL,
	objetivo VARCHAR(50),
	quantidade_concluido INT DEFAULT 0,
	progresso_atual INT DEFAULT 0,
	progresso_maximo INT,
	progresso_item VARCHAR(30),
	id_academico INT NOT NULL,
	tipo_situacao_meta_diaria VARCHAR(50) NOT NULL,
	CONSTRAINT fk_meta_diaria_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);