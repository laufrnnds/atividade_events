INSERT INTO tb_users (email, senha) VALUES ('laura@gmail.com', '$2a$12$Vy3ClU6lgMV5Vr8REZMyMuU7ll.RrJv2qoMGomSRKcpLT7dxVw912'), ('iolanda@gmail.com', '$2a$12$qaMoOxaIWBAU65upw8pe1uoJJYIZ6p6Bjjtdfl7hy7AbRenAy81De') ;

INSERT INTO tb_roles (authority) VALUES ('ROLE_ADMIN'), ('ROLE_CLIENT');

INSERT INTO tb_user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_roles (user_id, role_id) VALUES (2, 2);