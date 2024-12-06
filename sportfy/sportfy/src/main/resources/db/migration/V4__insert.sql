


INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, foto, permissao) VALUES ('igorn', 'pass', 'igor nathan lobato', '41997379627', 'Masculino', '2000-05-15', "https://midias.correiobraziliense.com.br/_midias/jpg/2023/07/04/640x853/1_siria-28423225.jpg?20230704211123?20230704211123", 0);

INSERT INTO academico (email, curso, id_usuario) VALUES ('igornathan@ufpr.br', 'tecnologia em análise e desenvolvimento de sistemas', 26);

INSERT INTO privacidade (id_academico, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (21, TRUE, TRUE, TRUE);

INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (21, 3);

INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (21, 9);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (21, 10);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (21, 11);

INSERT INTO campeonato (codigo, senha, titulo, descricao, aposta, data_inicio, data_fim, limite_times, limite_participantes, ativo, id_endereco, privacidade_campeonato, id_academico, id_modalidade_esportiva, situacao_campeonato, fase_atual)
VALUES ('#75EDK5',' ','Tenis de Mesa UFPR Oficial','Tenis de mesa entre alunos da UFPR!','10R$','2024-12-10 19:59:19','2024-12-15 19:59:00',16,1,TRUE,3,'PUBLICO',1,4,'EM_ABERTO',NULL);

INSERT INTO time (nome, id_campeonato)
VALUES
('math_aa', 9),
('maria_gn', 9),
('murilo_scn', 9),
('michele_as', 9),
('michael_ac', 9),
('maira_sm', 9),
('marcelo_ls', 9),
('muriel_ln', 9),
('michaela_ac', 9),
('mauro_vm', 9),
('aline_mr', 9),
('paulo_gm', 9),
('bruna_ls', 9),
('carlos_ap', 9),
('leticia_cp', 9),
('rafael_vb', 9);


INSERT INTO jogador (username, id_modalidade_esportiva, situacao_jogador, id_time, pontuacao, id_academico)
VALUES
('math_aa', 4, 0, 27, 0, 1),
('maria_gn', 4, 0, 28, 0, 2),
('murilo_scn', 4, 0, 29, 0, 3),
('michele_as', 4, 0, 30, 0, 4),
('michael_ac', 4, 0, 31, 0, 5),
('maira_sm', 4, 0, 32, 0, 6),
('marcelo_ls', 4, 0, 33, 0, 7),
('muriel_ln', 4, 0, 34, 0, 8),
('michaela_ac', 4, 0, 35, 0, 9),
('mauro_vm', 4, 0, 36, 0, 10),
('aline_mr', 4, 0, 37, 0, 16),
('paulo_gm', 4, 0, 38, 0, 17),
('bruna_ls', 4, 0, 39, 0, 18),
('carlos_ap', 4, 0, 40, 0, 19),
('leticia_cp', 4, 0, 41, 0, 20),
('rafael_vb', 4, 0, 42, 0, 21);

