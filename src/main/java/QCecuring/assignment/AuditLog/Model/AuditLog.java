package QCecuring.assignment.AuditLog.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Map;

@Document(collection = "audit_logs")
public class AuditLog {

    @Id
    private String id;

    private Instant timestamp = Instant.now(); // auto-set current time
    private String action;
    private String taskId;
    private Map<String, Object> updatedContent; // can be null for delete
    private String notes; // optional

    // Constructors
    public AuditLog() {}

    public AuditLog(String action, String taskId, Map<String, Object> updatedContent) {
        this.action = action;
        this.taskId = taskId;
        this.updatedContent = updatedContent;
        this.timestamp = Instant.now();
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public Map<String, Object> getUpdatedContent() { return updatedContent; }
    public void setUpdatedContent(Map<String, Object> updatedContent) { this.updatedContent = updatedContent; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
