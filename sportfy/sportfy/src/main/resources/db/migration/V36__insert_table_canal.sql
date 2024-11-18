INSERT INTO endereco (id_endereco, cep, uf, cidade, bairro, rua, numero, complemento)
VALUES (6, '80010000', 'PR', 'Curitiba', 'Centro', 'Rua das Flores', '123', 'Apartamento 201');

INSERT INTO endereco (id_endereco, cep, uf, cidade, bairro, rua, numero, complemento)
VALUES (7, '80020000', 'PR', 'Curitiba', 'Batel', 'Avenida Batel', '456', 'Bloco B');

INSERT INTO endereco (id_endereco, cep, uf, cidade, bairro, rua, numero, complemento)
VALUES (3, '80030000', 'PR', 'Curitiba', 'Alto da Glória', 'Rua Mateus Leme', '789', NULL);

INSERT INTO endereco (id_endereco, cep, uf, cidade, bairro, rua, numero, complemento)
VALUES (4, '80040000', 'PR', 'Curitiba', 'Santa Felicidade', 'Rua Itália', '101', 'Casa');

INSERT INTO endereco (id_endereco, cep, uf, cidade, bairro, rua, numero, complemento)
VALUES (5, '80050000', 'PR', 'Curitiba', 'Juvevê', 'Rua Doutor Goulin', '202', 'Próximo ao mercado');


INSERT INTO campeonato
(codigo, senha, titulo, descricao, aposta, data_inicio, data_fim, limite_times, limite_participantes, ativo, id_endereco, privacidade_campeonato, id_academico, id_modalidade_esportiva, situacao_campeonato, fase_atual)
VALUES
('#XPCQY1', '1234', 'Campeonato de Futebol', 'Campeonato regional de futebol', 'R$1000', '2024-12-01 10:00:00', '2024-12-15 18:00:00', 16, 11, TRUE, 3, 'PUBLICO', 1, 1, 'EM_ABERTO', 'OITAVAS'),
('#XPCQY2', '1234', 'Torneio de Basquete', 'Torneio estadual de basquete', 'R$500', '2024-11-20 14:00:00', '2024-11-30 16:00:00', 8, 1, TRUE, 3, 'PRIVADO', 2, 3, 'INICIADO', 'SEMI'),
('#XPCQY3', '1234', 'Campeonato de Vôlei', 'Competição municipal de vôlei', NULL, '2024-12-05 09:00:00', '2024-12-20 20:00:00', 12, 15, TRUE, 3, 'PUBLICO', 3, 2, 'EM_ABERTO', NULL),
('#XPCQY4', '1234', 'pingas de Rua', 'Melhor da cidade', 'Medalhas e troféus', '2024-11-25 07:00:00', '2024-11-25 12:00:00', 10, 1 , TRUE, 3, 'PUBLICO', 2, 4, 'FINALIZADO', 'FINAL'),
('#XPCQY5', '1234', 'Campeonato ufpr', 'Competição estadual da ufpr', 'R$2000', '2024-11-18 08:00:00', '2024-11-28 18:00:00', 10, 3, TRUE, 3, 'PRIVADO', 2, 1, 'INICIADO', 'QUARTAS');
