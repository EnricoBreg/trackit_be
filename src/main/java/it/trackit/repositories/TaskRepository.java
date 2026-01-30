package it.trackit.repositories;

import it.trackit.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
  Page<Task> findByProject_Id(UUID projectId, Pageable pageable);
}
