package QCecuring.assignment.AuditLog.Service;

import QCecuring.assignment.AuditLog.Model.AuditLog;
import QCecuring.assignment.AuditLog.Repo.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.data.domain.Sort;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Save a log entry
    public void logAction(String action, String taskId, Map<String, Object> updatedContent) {
        AuditLog log = new AuditLog(action, taskId, updatedContent);
        auditLogRepository.save(log);
    }

    public Page<AuditLog> getLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return auditLogRepository.findAll(pageable);
    }

}
