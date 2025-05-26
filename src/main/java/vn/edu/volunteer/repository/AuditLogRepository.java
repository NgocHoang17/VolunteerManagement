package vn.edu.volunteer.repository;

import vn.edu.volunteer.model.AuditLog;
import java.util.List;
import java.util.Date;
import java.util.Optional;

public interface AuditLogRepository {
    List<AuditLog> findAll();
    List<AuditLog> findAllWithPaging(int pageNumber, int pageSize);
    Optional<AuditLog> findById(Long id);
    AuditLog save(AuditLog auditLog);
    void deleteById(Long id);
    List<AuditLog> findByUsername(String username);
    List<AuditLog> findByAction(String action);
    List<AuditLog> findByTimestampBetween(Date startDate, Date endDate);
    List<AuditLog> searchLogs(String username, String action, Date startDate, Date endDate, int pageNumber, int pageSize);
    long count();
}