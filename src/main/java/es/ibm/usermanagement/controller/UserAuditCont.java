package es.ibm.usermanagement.controller;

import es.ibm.usermanagement.model.UserAudit;
import es.ibm.usermanagement.repository.UserAuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class UserAuditCont {
    @Autowired
    private UserAuditRepo userAuditRepository;

    // Maneja los GET para obtener el historial un usuario específico
    @GetMapping("/{uuid}")
    public List<UserAudit> obtenerHistorial(@PathVariable String uuid) {
        // Se llama al findByUuid del repo para obtener los datos de auditoría
        return userAuditRepository.findByUuid(uuid); //
    }
}
