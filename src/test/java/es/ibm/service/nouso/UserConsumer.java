package es.ibm.service.nouso;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ibm.usermanagement.kafka.dto.UserEventDTO;
import es.ibm.usermanagement.model.User;
import es.ibm.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    // Inyectamos el repo de usuarios para guardar los datos.
    @Autowired
    private UserRepository userRepository;

    // Convierte el JSON en un objeto Java (DTO).
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Este método se activa automáticamente cada vez que llega un "user-registration-events".
    // El groupId asegura forme parte del "user-group".
    @KafkaListener(topics = "user-registration-events", groupId = "user-group")
    public void consume(String message) {
        try {
            // Pasa el mensaje JSON a un DTO.
            UserEventDTO dto = objectMapper.readValue(message, UserEventDTO.class);

            // Chequeamos si ya existe un usuario con ese UUID.
            if (userRepository.existsById(dto.uuid)) {
                System.out.println("Usuario duplicado: " + dto.uuid);
                return;
            }

            // Creamos un nuevo objeto User con los datos del DTO.
            User user = new User();
            user.setUuid(dto.uuid);
            user.setNombre(dto.nombre);
            user.setApellidos(dto.apellidos);
            user.setEdad(dto.edad);
            user.setSuscripcion(dto.suscripcion);
            user.setCodigo_postal(dto.codigo_postal);

            // Guardamos el usuario.
            userRepository.save(user);

            System.out.println("Usuario guardado desde Kafka: " + dto.nombre);

        } catch (Exception e) {
            // Si algo sale mal, lo muestra por consola.
            System.err.println("Error procesando mensaje Kafka: " + e.getMessage());
        }
    }
}
