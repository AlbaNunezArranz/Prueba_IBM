package es.ibm.usermanagement.repository;

import es.ibm.usermanagement.model.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuditRepo extends JpaRepository<UserAudit, Long> {
    // Devuelve una lista de UserAudit basada en el UUID
    List<UserAudit> findByUuid(String uuid);
}
