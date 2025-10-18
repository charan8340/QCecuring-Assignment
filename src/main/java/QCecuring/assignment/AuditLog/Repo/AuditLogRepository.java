package QCecuring.assignment.AuditLog.Repo;

import QCecuring.assignment.AuditLog.Model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    // All CRUD operations + findAll with Pageable are already provided by MongoRepository
}
