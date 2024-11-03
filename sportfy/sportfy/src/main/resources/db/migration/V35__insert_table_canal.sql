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
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Corrida noturna', 'Alguém animado para uma corrida à noite? Podemos fazer um grupo pra isso.', 1, 5);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Yoga ao ar livre', 'Pessoal, pensei em fazer uma sessão de yoga ao ar livre. Alguém se interessa?', 1, 6);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Desafio semanal', 'Que tal uma meta de passos por dia? Quem chega a 10 mil todos os dias essa semana?', 1, 7);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Treino funcional', 'Vou fazer um treino funcional no parque. Quem topa?', 1, 8);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Futebol no sábado', 'Bora marcar um futebol para o próximo sábado? Precisamos de mais dois jogadores.', 1, 9);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Trilha no fim de semana', 'Quem tá afim de fazer uma trilha no domingo? Previsão de tempo tá ótima!', 1, 10);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Dicas para alongamento', 'Alguém tem dicas de alongamentos para fazer antes da corrida?', 1, 11);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Campeonato de vôlei', 'Estou organizando um campeonato de vôlei, quem quiser participar manda mensagem!', 1, 12);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Desafio de flexões', 'Quem consegue fazer 50 flexões por dia essa semana?', 1, 13);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Passeio ciclístico', 'Vamos organizar um passeio ciclístico para o próximo mês. Quem anima?', 1, 14);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Novas regras para futsal', 'Estava pensando em novas regras para nosso jogo de futsal, que tal discutirmos?', 1, 1);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Treino de natação', 'Quem quiser participar do treino de natação, confirme aqui!', 1, 2);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Maratona de filmes esportivos', 'Pessoal, maratona de filmes de esportes hoje à noite. Qualquer um pode vir!', 1, 3);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Aula de skate', 'Alguém com experiência no skate que possa ensinar o básico?', 1, 4);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Crossfit ao ar livre', 'Galera, bora fazer um treino de crossfit no parque? Preciso de companhia!', 1, 5);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Exercícios de respiração', 'Pessoal, aprendi alguns exercícios de respiração. Posso ensinar pra quem quiser.', 1, 6);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Boliche na sexta', 'Que tal uma partida de boliche na sexta-feira? Só para descontrair.', 1, 7);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Polo aquático', 'Alguém sabe jogar polo aquático? Podíamos montar um time.', 1, 8);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Treino HIIT', 'Proponho um treino HIIT amanhã de manhã. Quem topa?', 1, 9);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Racha de basquete', 'Quem anima para um racha de basquete amanhã à tarde?', 1, 10);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Arremesso de peso', 'Alguém tem dicas para melhorar no arremesso de peso?', 1, 11);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Treino de MMA', 'Galera, treino de MMA aberto para iniciantes e avançados!', 1, 12);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Caminhada para iniciantes', 'Caminhada para quem quer começar a se exercitar, vamos juntos!', 1, 13);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Corrida com obstáculos', 'Corrida com obstáculos na areia, quem se habilita?', 1, 14);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Aula de boxe', 'Aula de boxe para iniciantes, alguém interessado?', 1, 1);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Dicas de alimentação', 'Pessoal, alguém tem boas dicas de alimentação para atletas?', 1, 2);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Campeonato de tênis', 'Alguém sabe onde podemos organizar um campeonato de tênis?', 1, 3);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Golfe para todos', 'Sessão de golfe amanhã, quem quiser aprender mais sobre o esporte é bem-vindo!', 1, 4);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Futebol de areia', 'Vamos fazer um futebol de areia na praia sábado?', 1, 5);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Dicas de corrida', 'Algum corredor experiente para dar umas dicas?', 1, 6);
INSERT INTO publicacao (titulo, descricao, id_canal, id_usuario) VALUES ('Treino de alto rendimento', 'Estou montando uma rotina de treino de alto rendimento, interessados?', 1, 7);


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
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (10, 13);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (10, 14);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (10, 15);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (13, 8);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (16, 10);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (19, 4);
INSERT INTO curtida_publicacao (id_publicacao, id_usuario) VALUES (26, 8);


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
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Eu topo participar também!', 7, 3);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Pode contar comigo nesse treino!', 8, 6);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Alguém mais vai para essa corrida?', 9, 5);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Ótima ideia! Vamos marcar logo.', 10, 8);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Eu já faço isso todo dia, super apoio!', 11, 7);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Show, vou me organizar para estar lá!', 12, 9);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Isso vai ser épico!', 13, 10);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Podemos gravar para o canal também?', 14, 11);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Bora nessa!', 15, 12);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Ótima iniciativa! Com certeza estarei lá.', 15, 1);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Esse evento vai ser incrível!', 15, 2);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Alguém sabe a que horas vai começar?', 15, 3);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Contem comigo! Vou levar alguns amigos também.', 15, 4);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Essa é uma ótima oportunidade para aprender mais.', 15, 5);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Se precisarem de ajuda com a organização, podem me chamar.', 15, 6);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Estou muito animado para isso!', 15, 7);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Não vejo a hora! Vai ser ótimo.', 15, 8);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Boa! Faz tempo que espero por algo assim.', 15, 9);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Alguém vai de carona? Posso ajudar com a gasolina.', 15, 10);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Show de bola! Vou estar presente!', 15, 11);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Galera, posso filmar para registrar o evento?', 15, 12);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Quem aí está treinando para isso? Preciso de um parceiro.', 15, 13);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Vai ser bom demais, já estou ansioso!', 15, 14);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Levem água e lanches, vai ser um dia longo!', 15, 1);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Tava esperando por algo assim!', 16, 4);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Quem quiser dicas de flexão, só chamar!', 17, 13);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Eu sou iniciante mas quero tentar!', 18, 2);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Quem não quer perder essa, comenta aí!', 19, 14);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Super indico essa rotina, já fiz e é top!', 20, 1);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Alguém tem fotos de quando fizemos isso ano passado?', 21, 3);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Achei muito bom o último treino!', 22, 5);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Preciso dessa disciplina no meu treino também!', 23, 4);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Treino foi puxado, mas valeu muito!', 24, 6);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Top, vou me preparar para o próximo!', 25, 8);
INSERT INTO comentario (descricao, id_publicacao, id_usuario) VALUES ('Quem ainda não participou, recomendo muito!', 26, 9);


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
