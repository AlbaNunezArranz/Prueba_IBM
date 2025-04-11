package es.ibm.usermanagement.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_audit")
public class UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    // CREATE / UPDATE / DELETE
    private String accion;

    // En pruebas, por defecto "SYSTEM"
    private String usuario_responsable; // En pruebas, se fija como "SYSTEM"
    private Instant fecha;
    private String datos_usuario;

    public UserAudit() {
        this.fecha = Instant.now();
        // Simulado
        this.usuario_responsable = "SYSTEM";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUsuario_responsable() {
        return usuario_responsable;
    }

    public void setUsuario_responsable(String usuario_responsable) {
        this.usuario_responsable = usuario_responsable;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDatos_usuario() {
        return datos_usuario;
    }

    public void setDatos_usuario(String datos_usuario) {
        this.datos_usuario = datos_usuario;
    }
}
