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
