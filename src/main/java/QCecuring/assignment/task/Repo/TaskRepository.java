package QCecuring.assignment.task.Repo;

import QCecuring.assignment.task.Model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    // Search by title (case-insensitive)
    List<Task> findByTitleContainingIgnoreCase(String title);

    // Filter by keyword in description
    List<Task> findByDescriptionContainingIgnoreCase(String keyword);
}
