INSERT INTO campeonato
(codigo, senha, titulo, descricao, aposta, data_inicio, data_fim, limite_times, limite_participantes, ativo, id_endereco, privacidade_campeonato, id_academico, id_modalidade_esportiva, situacao_campeonato, fase_atual)
VALUES
('#75EDK4',' ','PingPong da amizade','Tenis de mesa entre amigos!','10R$','2024-12-10 19:59:19','2024-12-15 19:59:00',16,1,TRUE,3,'PUBLICO',1,4,'EM_ABERTO',NULL),
('#3RVXGD',' ','Tenis de mesa no SEPT 2024','campeonato intercursos.','20R$','2024-12-10 20:00:09','2024-12-15 19:59:00',16,1,TRUE,3,'PUBLICO',1,4,'EM_ABERTO',NULL),
('#6MUPE4',' ','Fut do TI','Campeonato dos cursos de ti!','Coca-Cola','2024-12-10 20:00:52','2024-12-15 19:59:00',16,11,TRUE,3,'PUBLICO',1,1,'EM_ABERTO',NULL);

INSERT INTO time
VALUES
(13,'Barcelona',8),
(14,'Maringa',8),
(15,'atletico piraquarense',8),
(16,'galaticos de santos',8),
(19,'maria_gn',6),
(20,'maria_gn',7),
(21,'murilo_scn',6),
(22,'murilo_scn',7),
(23,'michele_as',6),
(24,'michele_as',7),
(25,'michael_ac',6),
(26,'michael_ac',7);

INSERT INTO jogador
VALUES
(25,'maria_gn',4,0,0,19,2),
(26,'maria_gn',4,0,0,20,2),
(27,'maria_gn',1,0,0,13,2),
(28,'murilo_scn',1,0,0,14,3),
(29,'murilo_scn',4,0,0,21,3),
(30,'murilo_scn',4,0,0,22,3),
(31,'michele_as',1,0,0,16,4),
(32,'michele_as',4,0,0,23,4),
(33,'michele_as',4,0,0,24,4),
(34,'michael_ac',4,0,0,25,5),
(35,'michael_ac',4,0,0,26,5),
(36,'michael_ac',1,0,0,15,5);