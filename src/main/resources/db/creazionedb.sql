-- 1. Creazione dell'utente (Ruolo) con password
CREATE USER trackit WITH PASSWORD 'trackit';

-- 2. Creazione del database assegnando l'utente come proprietario
-- Nota: Uso 'track_it' minuscolo per convenzione standard PostgreSQL
CREATE DATABASE track_it OWNER trackit;

-- 1. Creazione dello schema 'dev' assegnando la proprietà all'utente trackit
CREATE SCHEMA dev AUTHORIZATION trackit;

-- 2. Impostazione del search_path per l'utente trackit
-- Questo fa sì che quando l'utente 'trackit' si connette,
-- PostgreSQL cercherà (e creerà) le tabelle dentro 'dev' automaticamente,
-- senza dover scrivere 'dev.nome_tabella'.
ALTER ROLE trackit SET search_path TO dev, public;

-- (Opzionale ma consigliato) Revoca i permessi di creazione nello schema public
-- per forzare l'uso dello schema dev e mantenere pulito il db.
REVOKE CREATE ON SCHEMA public FROM public;


-- Disattivazione di tutti gli utenti e connessioni aperte
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'track_it'
  AND pid <> pg_backend_pid();

-- Comandi per droppare il database e lo user
DROP DATABASE IF EXISTS track_it;
DROP USER IF EXISTS trackit;
