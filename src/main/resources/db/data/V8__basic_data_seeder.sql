--
-- INSERIMENTO DEL SUPER ADMIN
--
-- Password in chiaro: "password"
-- L'hash inizia con $2a$10$ ed è generato con BCrypt

INSERT INTO users (username, nome, cognome, email, password, global_role, is_active)
VALUES (
         'super',                                                         -- username
         'Super',                                                            -- nome
         'Admin',                                                         -- cognome
         'super@trackit.it',                                                 -- email
         '$2a$12$vU84zOQCeOpPLiVxsypTb.bS6uiBVMNFiooPYS3E60vtC.PMILHzG', -- password (hashata)
         'ROLE_SUPER_ADMIN',                                            -- is_super_admin
         TRUE                                                             -- is_active
       );


--
-- INSERIMENTO DEI RUOLI STANDARD
--
-- Livello 1: STAKEHOLDER
-- Il cliente o il commerciale che può solo guardare l'avanzamento ma non toccare nulla.
INSERT INTO project_roles (nome_ruolo, display_name, level) VALUES ('STAKEHOLDER', 'Stakeholder', 1);

-- Livello 2: QA_ENGINEER (Quality Assurance)
-- Può testare, aprire bug ticket e commentare, ma non modifica il codice o l'architettura.
INSERT INTO project_roles (nome_ruolo, display_name, level) VALUES ('QA_ENGINEER', 'QA Engineer', 2);

-- Livello 3: SOFTWARE_DEVELOPER
-- Il ruolo operativo standard: crea task, sposta task, scrive codice.
INSERT INTO project_roles (nome_ruolo, display_name, level) VALUES ('SOFTWARE_DEVELOPER', 'Software Developer', 3);

-- Livello 4: TECH_LEAD (o SOFTWARE_ARCHITECT)
-- Gestisce le scelte tecniche, assegna le task, ma non cancella il progetto.
INSERT INTO project_roles (nome_ruolo, display_name, level) VALUES ('TECH_LEAD', 'Technical Leader',  4);

-- Livello 5: PROJECT_MANAGER
-- Ha il controllo totale sul progetto (budget, membri, cancellazione).
INSERT INTO project_roles (nome_ruolo, display_name, level) VALUES ('PROJECT_MANAGER', 'Project Manager', 5);


