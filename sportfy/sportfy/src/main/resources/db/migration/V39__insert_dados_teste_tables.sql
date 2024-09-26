-- DADOS TESTE USUARIO
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('math_aa', 'pass', 'Matheus Antônio Augusto', '41987213343', '2002-03-03', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('maria_gn', 'pass', 'Maria Gabriela Naste', '41988762901', '1999-07-16', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('murilo_scn', 'pass', 'Murilo Souza Costa Neto', '41987690103', '2000-01-26', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('michele_as', 'pass', 'Michele Antoneli Silva', '41988906580', '1999-10-30', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('michael_ac', 'pass', 'Michael Andrew Curry', '41987665901', '1990-05-25', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('maira_sm', 'pass', 'Maíra Silverado Mendes', '41987665391', '2004-11-04', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('marcelo_ls', 'pass', 'Marcelo Leite Silveira', '41988778690', '1995-12-07', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('muriel_ln', 'pass', 'Muriel Leon Nogueira', '41987990809', '1991-06-28', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('michaela_ac', 'pass', 'Michaela Antonela Churatto', '41987676759', '2004-11-04', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('mauro_vm', 'pass', 'Mauro Vieira Marçal', '41988908780', '2003-12-13', 1);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('joao_nm', 'pass', 'Joao Nunes Moura', '41988876161', '1990-02-05', 3);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('jose_rm', 'pass', 'José Ricardo Moraes', '41989716532', '2005-10-14', 3);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('junior_bd', 'pass', 'Junior Bento Diniz', '41988915757', '2004-07-04', 2);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('jessica_cr', 'pass', 'Jessica Cristina Rocha', '41988980179', '2000-03-19', 2);
INSERT INTO usuario (username, password, nome, telefone, data_nascimento, id_permissao) VALUES ('jeronimo_cn', 'pass', 'Jerônimo Claudino Neves', '41987615454', '2002-04-21', 2);

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