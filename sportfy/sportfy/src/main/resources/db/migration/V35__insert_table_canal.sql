INSERT INTO canal (tipo_canal) VALUES ('COMUNIDADE');


-- DADOS TESTE USUARIO
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('math_aa', 'pass', 'Matheus Antônio Augusto', '41987213343', 'Masculino', '2002-03-03', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('maria_gn', 'pass', 'Maria Gabriela Naste', '41988762901', 'Feminino', '1999-07-16', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('murilo_scn', 'pass', 'Murilo Souza Costa Neto', '41987690103', 'Masculino', '2000-01-26', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('michele_as', 'pass', 'Michele Antoneli Silva', '41988906580', 'Feminino', '1999-10-30', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('michael_ac', 'pass', 'Michael Andrew Curry', '41987665901', 'Masculino', '1990-05-25', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('maira_sm', 'pass', 'Maíra Silverado Mendes', '41987665391', 'Outro', '2004-11-04', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('marcelo_ls', 'pass', 'Marcelo Leite Silveira', '41988778690', 'Masculino', '1995-12-07', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('muriel_ln', 'pass', 'Muriel Leon Nogueira', '41987990809', 'Masculino', '1991-06-28', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('michaela_ac', 'pass', 'Michaela Antonela Churatto', '41987676759', 'Feminino', '2004-11-04', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('mauro_vm', 'pass', 'Mauro Vieira Marçal', '41988908780', 'Masculino', '2003-12-13', 0);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('joao_nm', 'pass', 'Joao Nunes Moura', '41988876161', 'Masculino', '1990-02-05', 1);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('jose_rm', 'pass', 'José Ricardo Moraes', '41989716532', 'Masculino', '2005-10-14', 1);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('junior_bd', 'pass', 'Junior Bento Diniz', '41988915757', 'Masculino', '2004-07-04', 1);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('jessica_cr', 'pass', 'Jessica Cristina Rocha', '41988980179', 'Feminino', '2000-03-19', 1);
INSERT INTO usuario (username, password, nome, telefone, genero, data_nascimento, permissao) VALUES ('jeronimo_cn', 'pass', 'Jerônimo Claudino Neves', '41987615454', 'Masculino', '2002-04-21', 1);


-- DADOS TESTE ACADEMICO
INSERT INTO academico (email, curso, id_usuario) VALUES ('matheus@ufpr.br', 'Análise e Desenvolvimento de Sistemas', 1);
INSERT INTO academico (email, curso, id_usuario) VALUES ('maria@ufpr.br', 'Medicina', 2);
INSERT INTO academico (email, curso, id_usuario) VALUES ('murilo@ufpr.br', 'Psicologia', 3);
INSERT INTO academico (email, curso, id_usuario) VALUES ('michele@ufpr.br', 'Educação Física', 4);
INSERT INTO academico (email, curso, id_usuario) VALUES ('michael@ufpr.br', 'Matemática', 5);
INSERT INTO academico (email, curso, id_usuario) VALUES ('maira@ufpr.br', 'Biologia', 6);
INSERT INTO academico (email, curso, id_usuario) VALUES ('marcelo@ufpr.br', 'Biologia', 7);
INSERT INTO academico (email, curso, id_usuario) VALUES ('muriel@ufpr.br', 'Análise e Desenvolvimento de Sistemas', 8);
INSERT INTO academico (email, curso, id_usuario) VALUES ('michaela@ufpr.br', 'Análise e Desenvolvimento de Sistemas', 9);
INSERT INTO academico (email, curso, id_usuario) VALUES ('mauro@ufpr.br', 'Engenharia Mecânica', 10);


-- DADOS TESTE PRIVACIDADE
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (1, TRUE, FALSE, FALSE, FALSE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (2, TRUE, TRUE, FALSE, TRUE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (3, FALSE, FALSE, TRUE, FALSE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (4, TRUE, FALSE, TRUE, FALSE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (5, FALSE, FALSE, TRUE, FALSE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (6, TRUE, FALSE, TRUE, TRUE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (7, FALSE, FALSE, TRUE, TRUE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (8, TRUE, TRUE, FALSE, FALSE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (9, TRUE, TRUE, TRUE, TRUE);
INSERT INTO privacidade (id_academico, mostrar_modalidades_esportivas, mostrar_historico_campeonatos, mostrar_estatisticas_modalidades_esportivas, mostrar_conquistas) VALUES (10, TRUE, TRUE, TRUE, FALSE);


-- DADOS TESTE ADMINISTRADOR
INSERT INTO administrador (id_usuario) VALUES (11);
INSERT INTO administrador (id_usuario) VALUES (12);
INSERT INTO administrador (id_usuario) VALUES (13);
INSERT INTO administrador (id_usuario) VALUES (14);
INSERT INTO administrador (id_usuario) VALUES (15);


-- DADOS TESTE USUARIO_CANAL
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (1, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (2, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (3, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (4, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (5, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (6, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (7, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (8, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (9, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (10, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (11, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (12, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (13, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (14, 1);
INSERT INTO usuario_canal (id_usuario, id_canal) VALUES (15, 1);


-- DADOS TESTE PUBLICACAO
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Pign-pong amanhã', 'alguém afim de jogar um pingas amanhã? Se quiserem, eu vou criar um campeonato pra nós depois.', 1, 1);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Meta diária criativa', 'Boa tarde, alguém pode me dar uma sugestão de meta diária criativa para fazer?', 1, 2);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('meta de futebol', 'muito feliz que consegui completar minha meta de futebol!', 1, 3);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('fut domingo', 'Tava afim de participar de algum campeonato de futebol no próximo domingo, alguém topa?', 1, 4);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Modalidade para cinuca', 'Galera queria saber o que vocês acham de ter uma modalidade acadêmica para cinuca? Tava pensando em criar...', 1, 12);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('novidade', 'Pessoal olha o que eu descobri: prática esportiva oferece inúmeros benefícios para a saúde física e mental. Fisicamente, melhora a capacidade cardiovascular, fortalece os músculos e ossos, além de ajudar na manutenção de um peso saudável. Mentalmente, o esporte promove a liberação de endorfinas, reduzindo o estresse, a ansiedade e o risco de depressão. Também desenvolve habilidades sociais, como trabalho em equipe, e melhora a disciplina e a autoconfiança. Esses fatores contribuem para uma melhor qualidade de vida e bem-estar geral. Bora praticar mais.', 1, 8);


-- DADOS TESTE CURTIDA_PUBLICACAO
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 5);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 6);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 7);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 8);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 9);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (1, 10);

INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (2, 3);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (2, 6);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (2, 9);

INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 1);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 2);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 3);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 4);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 5);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 6);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 7);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 8);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 9);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 10);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 11);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 12);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 13);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 14);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (3, 15);

INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (5, 8);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (5, 10);

INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (6, 4);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (6, 6);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (6, 7);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (6, 8);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (6, 13);


-- DADOS TESTE COMENTARIO
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Eu consigo amanhã, bora bora', 1, 2);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('quase certeza que sim', 1, 7);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('consigo depois das 18h', 1, 8);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Vai ser dahora piazada, vou criar o campeonato às 18h então.', 1, 1);

INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('eu to fazendo um que estou achando bem legal de concluir o cubo mágico 3 vezes por dia', 2, 1);

INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Parabéns!!', 3, 1);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Nice!', 3, 2);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Bom demais!', 3, 4);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('É o braboo!!', 3, 5);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('você está impossível!', 3, 6);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Ensinando muito!!', 3, 7);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Garoto é diferenciado!', 3, 8);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('awesome!!', 3, 9);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Fera!!', 3, 10);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Neymar aprendeu contigo', 3, 11);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('boa, isso aí!', 3, 12);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('camisa 10 representando a federal!', 3, 13);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Brabíssimo!', 3, 14);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('joga y joga!', 3, 15);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Obrigado a todos pelas mensagens!', 3, 3);

INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Putz vou viajar se não eu participaria de algum também', 4, 1);

INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Eu gostei da ideia', 5, 4);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('boa, conheço alguns acadêmicos que jogam além de mim tbm', 5, 9);


-- DADOS TESTE CURTIDA_COMENTARIO
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (1, 1);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (10, 1);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (3, 2);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (6, 11);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (9, 11);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (10, 11);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (1, 19);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (11, 19);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (12, 19);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (7, 19);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (9, 21);
INSERT INTO curtida_comentario (id_usuario, id_comentario) VALUES (10, 21);


-- DADOS TESTE MODALIDADE_ESPORTIVA
INSERT INTO modalidade_esportiva (nome, descricao) VALUES ('Futebol', 'Esporte jogado por dois times, onde o objetivo é marcar gols movendo a bola com os pés. Vence quem marcar mais gols em dois tempos de partida.');
INSERT INTO modalidade_esportiva (nome, descricao) VALUES ('Vôlei', 'Dois times tentam fazer a bola tocar o chão do lado adversário, passando-a por cima de uma rede. Vence quem ganhar 3 sets.');
INSERT INTO modalidade_esportiva (nome, descricao) VALUES ('Basquete', 'Dois times tentam marcar pontos arremessando a bola na cesta do adversário. Ganha quem fizer mais pontos em quatro períodos.');
INSERT INTO modalidade_esportiva (nome, descricao) VALUES ('Handebol', 'Jogado por dois times, o objetivo é marcar gols arremessando a bola com as mãos no gol adversário. Vence quem fizer mais gols em dois tempos de partida.');
INSERT INTO modalidade_esportiva (nome, descricao) VALUES ('Tênis de Mesa', 'Jogadores usam raquetes para golpear uma bola em uma mesa com rede. O objetivo é fazer o adversário errar a devolução. Vence quem ganhar mais sets.');


-- DADOS TESTE MODALIDADE_ESPORTIVA
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Gols', 'Um gol é válido quando a bola ultrapassa completamente a linha do gol.', 1);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Faltas', 'Em caso de falta grave, o jogador pode ser expulso com cartão vermelho.', 1);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Toque', 'Cada equipe pode tocar a bola até 3 vezes antes de enviá-la ao lado adversário.', 2);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Contato', 'O contato físico excessivo é considerado falta.', 3);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Pontuações', 'Cada cesta vale 2 pontos, exceto cestas de longa distância, que valem 3, e lances livres, que valem 1.', 3);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Área do Gol', 'O goleiro é o único que pode tocar a bola dentro da área de gol.', 4);
INSERT INTO regra (titulo, descricao, id_modalidade_esportiva) VALUES ('Quick', 'O ponto é perdido se a bola não quicar na mesa do adversário ou se o jogador não devolver corretamente.', 5);


-- DADOS TESTE META_ESPORTIVA
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Gols de Bicicleta', 'Fazer 10 gols de bicicleta.', 1);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Gols de Pênalti', 'Fazer 30 gols de bicicleta.', 1);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Saque Maestria', 'Fazer 10 pontos de saque.', 2);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Bloqueio Eficaz', 'Fazer 50 bloqueios de sucesso.', 2);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Anjo da Guarda', 'Fazer 50 manchetes de defesa com êxito.', 2);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Mestre da Enterrada', 'Fazer 50 enterradas com êxito.', 3);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Melhor amigo', 'Fazer 40 assistências em gols para a equipe.', 4);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Perspicaz', 'Interceptar ou recuperar 50 vezes a posse de bola.', 4);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Muralha', 'Executar 20 defesas com êxito como goleiro em uma única partida.', 4);
INSERT INTO meta_esportiva (titulo, descricao, id_modalidade_esportiva) VALUES ('Fatiador', 'Realizar 100 cortes com êxito.', 5);


-- DADOS TESTE ACADEMICO_MODALIDADE_ESPORTIVA
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (1, 1);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (1, 2);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (1, 3);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (1, 4);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (1, 5);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (2, 2);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (2, 4);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (3, 1);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (3, 3);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (3, 5);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (4, 5);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (5, 4);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (6, 2);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (6, 3);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (6, 4);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (6, 5);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (7, 1);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (7, 2);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (8, 4);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (8, 5);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (10, 1);
INSERT INTO academico_modalidade_esportiva (id_academico, id_modalidade_esportiva) VALUES (10, 5);


-- DADOS TESTE CONQUISTA
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (1, 1);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (1, 3);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (2, 2);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (2, 4);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (3, 3);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (3, 5);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (4, 5);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (5, 4);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (6, 3);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (6, 5);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (7, 1);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (8, 4);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (8, 5);
INSERT INTO conquista (id_academico, id_meta_esportiva) VALUES (10, 5);
