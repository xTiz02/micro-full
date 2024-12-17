create database if not exists MicroFullAuth;
use MicroFullAuth;

INSERT INTO microfullauth.roles (role_enum, id, description) VALUES (1, 0x23405BC507C144968893A325D3152381, 'User role');
INSERT INTO microfullauth.roles (role_enum, id, description) VALUES (0, 0x282E6145DA2341E2B11EA87BA59B30D8, 'Admin role');

#Pass 12345
INSERT INTO microfullauth.users (account_expired, account_locked, credentials_expired, enabled, id, role_id, email, password, username) VALUES (false, false, false, true, 0xE20A58680ACA4173AE91568FD533093B, 0x282E6145DA2341E2B11EA87BA59B30D8, 'admin@gmail.com', '$2a$10$GaE6DV3O1UjnbelnR4zhxObAqavRDcVaglzPX7okMiUQpjiLvsVoS', 'admin');
INSERT INTO microfullauth.users (account_expired, account_locked, credentials_expired, enabled, id, role_id, email, password, username) VALUES (false, false, false, true, 0xE30A58680ACA4173AE91568FD533092B, 0x23405BC507C144968893A325D3152381, 'user@gmail.com', '$2a$10$GaE6DV3O1UjnbelnR4zhxObAqavRDcVaglzPX7okMiUQpjiLvsVoS', 'user');