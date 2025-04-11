package es.ibm.usermanagement.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ibm.usermanagement.kafka.dto.UserEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserProducer {

    private static final String TOPIC = "user-registration-events";

    @Autowired
    private KafkaTemplate<String, UserEventDTO> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void enviarUsuario(UserEventDTO dto) {
        try {
            // Convertimos el DTO a JSON¡
            String mensaje = objectMapper.writeValueAsString(dto);
            // Enviamos el mensaje a Kafka
            kafkaTemplate.send(TOPIC, dto.uuid, dto);
            System.out.println("Mensaje enviado: " + mensaje);

        } catch (Exception e) {
            System.err.println("Error al enviar mensaje a Kafka: " + e.getMessage());
        }
    }

    public void enviarEventoUsuario(UserEventDTO dto) {
        enviarUsuario(dto);
    }
}
