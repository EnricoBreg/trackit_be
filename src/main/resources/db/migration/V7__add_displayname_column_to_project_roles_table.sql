ALTER TABLE project_roles
  ADD display_name VARCHAR(255);
UPDATE project_roles SET display_name = nome_ruolo
WHERE project_roles.display_name IS NULL;
ALTER TABLE project_roles ALTER COLUMN display_name SET NOT NULL;
