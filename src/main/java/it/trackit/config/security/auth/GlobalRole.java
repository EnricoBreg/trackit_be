package it.trackit.config.security.auth;

/**
 * GlobalRole definisce un insieme di ruoli che rappresentano la gerarchia
 * e le responsabilità degli utenti all'interno dell'applicazione a livello globale.
 *
 * <p>I ruoli vengono utilizzati per gestire l'accesso e i privilegi degli utenti
 * in tutto il sistema. Essi determinano l'ambito di autorità di un utente e
 * le azioni che è autorizzato a eseguire nell'applicazione.</p>
 *
 * <p>I ruoli definiti in questa enum seguono una struttura gerarchica:</p>
 * <ul>
 *   <li>{@code SUPER_ADMIN}: Possiede il massimo livello di autorità, inclusi
 *       il controllo amministrativo su tutti gli utenti e su tutti i permessi.</li>
 *   <li>{@code ADMIN}: Dispone di privilegi amministrativi, generalmente limitati
 *       alla gestione di utenti e risorse all'interno di uno specifico ambito.</li>
 *   <li>{@code USER}: Rappresenta un utente standard con accesso di base
 *       alle funzionalità del sistema.</li>
 * </ul>
 */
public enum GlobalRole {
  ROLE_SUPER_ADMIN,
  ROLE_ADMIN,
  ROLE_USER
}
