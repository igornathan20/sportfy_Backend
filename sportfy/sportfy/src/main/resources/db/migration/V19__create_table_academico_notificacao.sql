CREATE TABLE academico_notificacao (
	id_academico_notificacao INT AUTO_INCREMENT PRIMARY KEY,
	id_notificacao INT NOT NULL,
	id_academico INT NOT NULL,
	CONSTRAINT fk_academico_notificacao_notificacao FOREIGN KEY (id_notificacao) REFERENCES notificacao(id_notificacao),
	CONSTRAINT fk_academico_notificacao_academico FOREIGN KEY (id_academico) REFERENCES academico(id_academico)
);