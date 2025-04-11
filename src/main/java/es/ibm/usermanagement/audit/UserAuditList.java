package es.ibm.usermanagement.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ibm.usermanagement.model.User;
import es.ibm.usermanagement.model.UserAudit;
import es.ibm.usermanagement.repository.UserAuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

// Intercepta operaciones de CREATE o UPDATE/
// sobre los User, y guarda un registro en la tabla.
@Component
public class UserAuditList {

    private static UserAuditRepo staticAuditRepo;

    @Autowired
    public void init(UserAuditRepo repository) {
        UserAuditList.staticAuditRepo = repository;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Se ejecuta después de guardar o actualizar un User.
    @PostPersist
    @PostUpdate
    public void audit(Object obj) {
        try {
            if (obj instanceof User) {
                User user = (User) obj;

                UserAudit audit = new UserAudit();
                audit.setUuid(user.getUuid());
                audit.setAccion(user.getFecha_creacion() != null ? "CREATE" : "UPDATE");
                audit.setDatos_usuario(objectMapper.writeValueAsString(user));
                staticAuditRepo.save(audit);
            }
        } catch (Exception e) {
            // Muestra el mensaje de error por consola
            System.err.println("Error guardando auditoría: " + e.getMessage());
        }
    }
}
