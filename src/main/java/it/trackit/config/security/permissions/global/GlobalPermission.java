package it.trackit.config.security.permissions.global;

/**
 * GlobalPermission definisce un insieme di permessi globali che possono
 * essere utilizzati per eseguire azioni di livello amministrativo
 * all'interno dell'applicazione.
 *
 * <p>I permessi definiti in questa enum sono pensati per gestire operazioni
 * critiche e per amministrare il ruolo dell'utente all'interno del sistema.</p>
 *
 * <p><strong>Permessi globali disponibili:</strong></p>
 * <ul>
 *   <li><b>USER_RESET_PASSWORD</b>: Permesso per reimpostare la password degli utenti.</li>
 *   <li><b>USER_ENABLE_DISABLE</b>: Permesso per abilitare o disabilitare gli utenti.</li>
 *   <li><b>USER_ROLE_ASSIGN</b>: Permesso per assegnare o modificare i ruoli degli utenti.</li>
 * </ul>
 */
public enum GlobalPermission {
  // Users
  USER_CREATE,
  USER_EDIT,
  USER_DELETE,
  USER_RESET_PASSWORD,
  USER_ENABLE_DISABLE,
  USER_ROLE_ASSIGN,

  // Project
  PROJECT_CREATE,
  PROJECT_EDIT,
  PROJECT_DELETE,
}
