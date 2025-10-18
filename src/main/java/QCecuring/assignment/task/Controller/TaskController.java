package QCecuring.assignment.task.Controller;

import QCecuring.assignment.task.Model.Task;
import QCecuring.assignment.task.Service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "*") // allow frontend requests from any domain
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ✅ Create a new Task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty() ||
                task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(null); // or you can return a custom error message
        }

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    // ✅ Update Task by ID
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        if (updatedTask.getTitle() == null || updatedTask.getTitle().trim().isEmpty() ||
                updatedTask.getDescription() == null || updatedTask.getDescription().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(null); // or return a custom message
        }

        Optional<Task> task = taskService.updateTask(id, updatedTask);
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get all Tasks
    @GetMapping("health-check")
    public String health(){
        return "WOrking ";
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // ✅ Get Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete Task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String title) {
        return ResponseEntity.ok(taskService.searchTasks(title));
    }

    // ✅ Filter by description
    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.filterTasks(keyword));
    }

}
