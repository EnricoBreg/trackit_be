package it.trackit.repositories;

import it.trackit.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByProject_Id(UUID projectId);
}
