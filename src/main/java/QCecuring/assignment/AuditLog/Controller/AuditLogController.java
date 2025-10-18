package QCecuring.assignment.AuditLog.Controller;

import QCecuring.assignment.AuditLog.Model.AuditLog;
import QCecuring.assignment.AuditLog.Service.AuditLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogService auditLogService;
    private final PagedResourcesAssembler<AuditLog> assembler;

    public AuditLogController(AuditLogService auditLogService, PagedResourcesAssembler<AuditLog> assembler) {
        this.auditLogService = auditLogService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AuditLog>>> getLogs(
            @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<AuditLog> logsPage = auditLogService.getLogs(pageable.getPageNumber(), pageable.getPageSize());
        PagedModel<EntityModel<AuditLog>> model = assembler.toModel(logsPage);
        return ResponseEntity.ok(model);
    }
}
