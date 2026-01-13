package it.trackit.config.security.permissions.project;

/**
 * ProjectPermission definisce un insieme di permessi specifici per i progetti
 * all'interno dell'applicazione.
 *
 * <p>Questi permessi regolano le azioni che gli utenti possono eseguire sui membri
 * del progetto, nonché sulle attività o segnalazioni (issue) del progetto.</p>
 *
 * <p><strong>Permessi relativi ai membri del progetto:</strong></p>
 * <ul>
 *   <li><b>MEMBER_VIEW</b>: Permesso di visualizzare i membri del progetto.</li>
 *   <li><b>MEMBER_EDIT</b>: Permesso di modificare i dettagli dei membri del progetto.</li>
 *   <li><b>MEMBER_DELETE</b>: Permesso di rimuovere i membri dal progetto.</li>
 * </ul>
 *
 * <p><strong>Permessi relativi alle attività o segnalazioni del progetto:</strong></p>
 * <ul>
 *   <li><b>ISSUE_CREATE</b>: Permesso di creare nuove attività o segnalazioni.</li>
 *   <li><b>ISSUE_EDIT</b>: Permesso di modificare i dettagli di attività o segnalazioni esistenti.</li>
 *   <li><b>ISSUE_DELETE</b>: Permesso di eliminare attività o segnalazioni dal progetto.</li>
 *   <li><b>ISSUE_VIEW</b>: Permesso di visualizzare i dettagli di attività o segnalazioni.</li>
 *   <li><b>ISSUE_ASSIGN</b>: Permesso di assegnare attività o segnalazioni ai membri del progetto.</li>
 * </ul>
 */
public enum ProjectPermission {
  // Users
  MEMBER_VIEW,
  MEMBER_EDIT,
  MEMBER_DELETE,

  // Tasks
  ISSUE_CREATE,
  ISSUE_EDIT,
  ISSUE_DELETE,
  ISSUE_VIEW,
  ISSUE_ASSIGN
}
