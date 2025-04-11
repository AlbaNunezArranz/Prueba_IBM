package es.ibm.usermanagement.model;

import es.ibm.usermanagement.audit.UserAuditList;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@EntityListeners(UserAuditList.class)
@Table(name = "usuarios")
public class User {
    // Se utiliza la anotación @Id para indicar que el campo 'uuid' es la pk.
    @Id
    // Esta anotación se utiliza para la generación automática del valor de 'uuid'.
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;

    private String nombre;
    private String apellidos;
    private int edad;
    private boolean suscripcion;
    private String codigo_postal;
    private Instant fecha_creacion;

    // Inicializa el 'uuid' y 'fecha_creacion' automáticamente.
    public User() {
        // Genera un UUID único, elimina los guiones y corta los primeros 16 caracteres.
        this.uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        // Asigna la fecha y hora actual al campo 'fecha_creacion'.
        this.fecha_creacion = Instant.now();
    }

    public User(String uuid, String nombre, String apellidos, int edad, boolean suscripcion, String codigo_postal, Instant fecha_creacion) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.suscripcion = suscripcion;
        this.codigo_postal = codigo_postal;
        this.fecha_creacion = fecha_creacion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(boolean suscripcion) {
        this.suscripcion = suscripcion;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public Instant getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Instant fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}