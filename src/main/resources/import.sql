insert into cozinha (id,nome) values (1,'Tailandesa');
insert into cozinha (id,nome) values (2,'Indiana');

insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1,'Thai Gourmet', 10,1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2,'Thai Delivery', 9.50, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (3, 'Tuk Tuk Comida Indiana', 15,2);

insert into forma_pagamento(id, descricao) values(1, 'dinheiro');
insert into forma_pagamento(id, descricao) values(2, 'crédito');
insert into forma_pagamento(id, descricao) values(3, 'débito');

insert into permissao(id, nome, descricao)values(1, 'Consultar', 'Permite ao usuário efetuar consultas');
insert into permissao(id, nome, descricao)values(2, 'Alterar', 'Permite ao usuário efetuar alterações no sistema'); 

insert into estado(id, nome) values(1, 'Rio Grande do Sul');
insert into estado(id, nome) values(2, 'Santa Catarina');
insert into estado(id, nome) values(3, 'Paraná');

insert into cidade(id, nome, estado_id) values(1, 'Alvorada', 1);
insert into cidade(id, nome, estado_id) values(2, 'Porto Alegre', 1);
insert into cidade(id, nome, estado_id) values(3, 'Portão', 1);
