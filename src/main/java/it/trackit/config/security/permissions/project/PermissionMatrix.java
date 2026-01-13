package it.trackit.config.security.permissions.project;

import java.util.Map;
import java.util.Set;

import static it.trackit.config.security.permissions.project.ProjectPermission.*;

/**
 * PermissionMatrix definisce una mappatura tra i livelli di accesso
 * e specifici insiemi di permessi di progetto.
 *
 * <p>Questa classe viene utilizzata per gestire e recuperare i permessi
 * associati ai diversi livelli di accesso nel contesto delle azioni
 * relative ai progetti.</p>
 *
 * <p>Ogni livello di accesso è collegato a un insieme predefinito di valori
 * {@code ProjectPermission}, che specificano in modo esplicito le
 * capacità assegnate agli utenti a quel livello.</p>
 *
 * <p><strong>Livelli di accesso:</strong></p>
 * <ul>
 *   <li><b>Livello 1</b>: Permessi di visualizzazione di base, inclusa la
 *       visualizzazione dei membri del progetto.</li>
 *   <li><b>Livello 2</b>: Oltre ai permessi del Livello 1, include la
 *       visualizzazione delle attività/segnalazioni.</li>
 *   <li><b>Livello 3</b>: Aggiunge la possibilità di creare e modificare
 *       attività/segnalazioni.</li>
 *   <li><b>Livello 4</b>: Estende il Livello 3 con la possibilità di
 *       modificare i membri del progetto.</li>
 *   <li><b>Livello 5</b>: Concede tutti i permessi relativi al progetto
 *       disponibili.</li>
 * </ul>
 *
 * <p>La funzionalità principale fornita da questa classe è il recupero
 * dei permessi associati a un determinato livello di accesso.</p>
 */
public class PermissionMatrix {

  private static final Map<Integer, Set<ProjectPermission>> LEVEL_PERMISSIONS_MAP =
    Map.of(
      1, Set.of(MEMBER_VIEW),
      2, Set.of(MEMBER_VIEW, ISSUE_VIEW),
      3, Set.of(MEMBER_VIEW, ISSUE_CREATE, ISSUE_EDIT),
      4, Set.of(MEMBER_VIEW, MEMBER_EDIT, ISSUE_CREATE, ISSUE_EDIT),
      5, Set.of(ProjectPermission.values())
    );

  public static Set<ProjectPermission> getPermissionsForLevel(Integer level) {
    if (level == null) return Set.of();
    return LEVEL_PERMISSIONS_MAP.getOrDefault(level, Set.of());
  }
}
