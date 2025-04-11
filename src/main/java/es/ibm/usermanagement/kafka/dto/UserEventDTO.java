package es.ibm.usermanagement.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserEventDTO {
    // Generar un UUID por defecto
    @JsonProperty("uuid")
    public String uuid = UUID.randomUUID().toString();

    @JsonProperty("nombre")
    public String nombre;

    @JsonProperty("apellidos")
    public String apellidos;

    @JsonProperty("edad")
    public int edad;

    @JsonProperty("suscripcion")
    public boolean suscripcion;

    @JsonProperty("codigo_postal")
    public String codigo_postal;
}
