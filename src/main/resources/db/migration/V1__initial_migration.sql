CREATE TABLE users (
  id SERIAL NOT NULL,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password TEXT NOT NULL,
  is_super_admin BOOLEAN NOT NULL DEFAULT FALSE,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT users_username_uk UNIQUE (username),
  CONSTRAINT users_email_uk UNIQUE (email)
);

CREATE TABLE projects (
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  nome VARCHAR(200) NOT NULL,
  descrizione TEXT NOT NULL,
  stato VARCHAR(100) NOT NULL,
  data_creazione DATE NOT NULL DEFAULT CURRENT_DATE,
  data_inizio_lavorazione DATE,
  data_ultima_modifica DATE,
  data_scadenza DATE NOT NULL,
  data_prevista_chiusura DATE,
  data_chiusura DATE,
  updated_at DATE,
  ended_at DATE,
  CONSTRAINT projects_pk PRIMARY KEY (uuid),
  CONSTRAINT projects_nome_uk UNIQUE (nome)
);

CREATE TABLE project_roles (
  id SERIAL NOT NULL,
  nome_ruolo VARCHAR(200) NOT NULL,
  CONSTRAINT project_roles_pk PRIMARY KEY (id)
);

CREATE TABLE project_members (
  project_id UUID NOT NULL,
  user_id INTEGER NOT NULL,
  project_role_id INTEGER NOT NULL,
  CONSTRAINT project_members_pk PRIMARY KEY (user_id, project_id),
  CONSTRAINT project_members_project_fk
   FOREIGN KEY (project_id) REFERENCES projects (uuid),
  CONSTRAINT project_members_user_fk
   FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT project_members_role_fk
   FOREIGN KEY (project_role_id) REFERENCES project_roles (id)
);

COMMENT ON TABLE project_members
  IS 'Tabella di join per appartenenza ad un progetto e relativo ruolo';

CREATE TABLE tasks (
  id SERIAL NOT NULL,
  titolo VARCHAR(200) NOT NULL,
  descrizione TEXT NOT NULL,
  stato VARCHAR(100) NOT NULL,
  priorita VARCHAR(100) NOT NULL,
  progresso INTEGER NOT NULL DEFAULT 0,
  data_creazione DATE NOT NULL DEFAULT CURRENT_DATE,
  data_assegnazione DATE,
  data_inizio_lavorazione DATE,
  data_ultima_modifica DATE NOT NULL DEFAULT CURRENT_DATE,
  data_scadenza DATE NOT NULL,
  data_chiusura DATE,
  project_id UUID NOT NULL,
  assignee_id INTEGER,
  reporter_id INTEGER NOT NULL,
  parent_task_id INTEGER,
  CONSTRAINT tasks_pk PRIMARY KEY (id),
  CONSTRAINT tasks_project_fk
   FOREIGN KEY (project_id) REFERENCES projects (uuid),
  CONSTRAINT tasks_parent_task_fk
   FOREIGN KEY (parent_task_id) REFERENCES tasks (id),
  CONSTRAINT tasks_assignee_fk
   FOREIGN KEY (assignee_id) REFERENCES users (id),
  CONSTRAINT tasks_reporter_fk
   FOREIGN KEY (reporter_id) REFERENCES users (id)
);

CREATE TABLE comments (
  id SERIAL NOT NULL,
  testo TEXT NOT NULL,
  created_at DATE NOT NULL DEFAULT CURRENT_DATE,
  task_id INTEGER,
  user_id INTEGER,
  CONSTRAINT comments_pk PRIMARY KEY (id),
  CONSTRAINT comments_task_fk
    FOREIGN KEY (task_id) REFERENCES tasks (id),
  CONSTRAINT comments_user_fk
    FOREIGN KEY (user_id) REFERENCES users (id)
);
