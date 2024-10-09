create database if not exists MicroFullAuth;
use MicroFullAuth;

INSERT INTO microfullauth.roles (role_enum, id, description) VALUES (1, 0x23405BC507C144968893A325D3152381, 'User role');
INSERT INTO microfullauth.roles (role_enum, id, description) VALUES (0, 0x282E6145DA2341E2B11EA87BA59B30D8, 'Admin role');