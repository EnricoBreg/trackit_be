package it.trackit.repositories;

import it.trackit.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :searchText, '%'))
      OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :searchText, '%'))
      OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchText, '%'))
  """)
  Page<User> searchUsers(@Param("searchText") String searchTerm, Pageable pageable);
}
