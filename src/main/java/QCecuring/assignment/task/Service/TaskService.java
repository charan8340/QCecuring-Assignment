package QCecuring.assignment.task.Service;

import QCecuring.assignment.AuditLog.Service.AuditLogService;
import QCecuring.assignment.task.Model.Task;
import QCecuring.assignment.task.Repo.TaskRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AuditLogService auditLogService;

    public TaskService(TaskRepository taskRepository, AuditLogService auditLogService) {
        this.taskRepository = taskRepository;
        this.auditLogService = auditLogService;
    }

    // ✅ Create a new task
    public Task createTask(Task task) {
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        Task savedTask = taskRepository.save(task);

        // Log creation
        Map<String, Object> details = new HashMap<>();
        details.put("title", savedTask.getTitle());
        details.put("description", savedTask.getDescription());
        auditLogService.logAction("CREATE", savedTask.getId(), details);

        return savedTask;
    }

    // ✅ Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ✅ Get task by ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // ✅ Update task
    public Optional<Task> updateTask(String id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            Map<String, Object> changes = new HashMap<>();

            if (!existingTask.getTitle().equals(updatedTask.getTitle())) {
                Map<String, Object> diff = new HashMap<>();
                diff.put("old", existingTask.getTitle());
                diff.put("new", updatedTask.getTitle());
                changes.put("title", diff);
                existingTask.setTitle(updatedTask.getTitle());
            }

            if (!existingTask.getDescription().equals(updatedTask.getDescription())) {
                Map<String, Object> diff = new HashMap<>();
                diff.put("old", existingTask.getDescription());
                diff.put("new", updatedTask.getDescription());
                changes.put("description", diff);
                existingTask.setDescription(updatedTask.getDescription());
            }

            existingTask.setUpdatedAt(Instant.now());
            Task savedTask = taskRepository.save(existingTask);

            // Log update
            if (!changes.isEmpty()) {
                auditLogService.logAction("UPDATE", savedTask.getId(), changes);
            }

            return savedTask;
        });
    }

    // ✅ Delete a task by ID
    public void deleteTask(String id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        existingTask.ifPresent(task -> {
            taskRepository.deleteById(id);
            // Log deletion
            auditLogService.logAction("DELETE", task.getId(), null);
        });
    }
    // ✅ Search tasks by title
    public List<Task> searchTasks(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    // ✅ Filter tasks by description keyword
    public List<Task> filterTasks(String keyword) {
        return taskRepository.findByDescriptionContainingIgnoreCase(keyword);
    }

}
