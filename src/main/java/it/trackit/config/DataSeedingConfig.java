package it.trackit.config;

import it.trackit.config.security.permissions.global.GlobalRole;
import it.trackit.entities.Project;
import it.trackit.entities.User;
import it.trackit.repositories.ProjectRepository;
import it.trackit.repositories.ProjectRoleRepository;
import it.trackit.repositories.TaskRepository;
import it.trackit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Configuration
@AllArgsConstructor
@Profile({"dev"})
@Slf4j
public class DataSeedingConfig {

  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final ProjectRoleRepository projectRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final TaskRepository taskRepository;

  @Bean
  CommandLineRunner initDatabase(
  ) {
    return args -> {
      log.info("Inizio del seeding del database...");

      seedUsers();
      seedProjects();
      seedTasks();

      log.info("Fine seeding del database.");
    };
  }

  /*
   * Aggiunta di progetti
   */
  private void seedUsers() {
    log.info("Inizio del seeding degli utenti...");
    if (userRepository.count() > 1) {
      log.warn("Il database contiene già dati per gli utenti. Seeding utenti saltato.");
      return;
    }

    List<User> utenti = new ArrayList<>();

    Faker faker = new Faker(Locale.ITALY);

    IntStream.range(0, 20).forEach(i -> {
      String nome = faker.name().firstName();
      String cognome = faker.name().lastName();
      String username = (nome + "." + cognome).toLowerCase().replaceAll("'", "").replaceAll(" ", "");
      String email = username + "@trackit.it";
      String password = passwordEncoder.encode("trackit123");

      var utente = new User();
      utente.setNome(nome);
      utente.setCognome(cognome);
      utente.setUsername(username);
      utente.setEmail(email);
      utente.setPassword(password);
      utente.setIsActive(true);
      utente.setGlobalRole(GlobalRole.ROLE_USER);

      utenti.add(utente);

      log.info("Creato utente {} {} con username {}", nome, cognome, username);
    });

    userRepository.saveAll(utenti);

    log.info("Fine seeding degli utenti.");
  }

  /*
   * Aggiunta di progetti
   */
  private void seedProjects() {
    log.info("Inizio del seeding dei progetti...");

    if (projectRepository.count() > 1) {
      log.warn("Il database contiene già dati per i progetti. Seeding progetti saltato.");
      return;
    }

    List<Project> progetti = new ArrayList<>();

    Faker faker = new Faker(Locale.ITALY);

    IntStream.range(0, 3).forEach(i -> {
      String nomeProgetto = faker.lorem().sentence(3);
      String descrizioneProgetto = faker.lorem().sentence(new Random().nextInt(10) + 10);
      Project.Stato stato = faker.options().option(Project.Stato.class);
      LocalDateTime createdAt =  LocalDateTime.ofInstant(faker.timeAndDate().past(60, TimeUnit.DAYS),
                                                         ZoneId.of("UTC"));
      LocalDateTime updatedAt = LocalDateTime.now();

      var progetto = new Project();
      progetto.setNome(nomeProgetto);
      progetto.setDescrizione(descrizioneProgetto);
      progetto.setStato(stato);
      progetto.setDataCreazione(createdAt);
      progetto.setDataUltimaModifica(updatedAt);

      progetti.add(progetto);

      log.info("Creato progetto con nome {}", nomeProgetto);
    });

    projectRepository.saveAll(progetti);

    log.info("Fine seeding dei progetti.");
  }

  /*
   * Aggiunta di progetti
   */
  private void seedTasks() {
  }
}
