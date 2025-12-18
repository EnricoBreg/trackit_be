package it.trackit.repositories;

import it.trackit.entities.ProjectMember;
import it.trackit.entities.ProjectMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberKey> {

  // Trova tutti i progetti di un utente
  //  Spring legge così:
  //  findBy...
  //  User: Cerca il campo user dentro ProjectMember. (Lo trova! È la @ManyToOne).
  //  _: Entra dentro l'oggetto User.
  //  Id: Cerca il campo userId dentro l'oggetto User. (Lo trova!).
  List<ProjectMember> findByUser_Id(Long userId);

  // Trova un membro specifico (per vedere il ruolo)
  // Spring capisce automaticamente come cercare dentro la chiave composta
  Optional<ProjectMember> findByProject_IdAndUserId(UUID projectId, Long userId);
}
