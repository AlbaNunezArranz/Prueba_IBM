package es.ibm.usermanagement.controller;

import es.ibm.usermanagement.kafka.dto.UserEventDTO;
import es.ibm.usermanagement.kafka.KafkaUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {
    @Autowired
    // Instancia que se utiliza para enviar eventos
    private KafkaUserProducer producer;

    // Ruta POST para publicar un evento en Kafka.
    @PostMapping("/publish")
    public ResponseEntity<String> publicarEvento(@RequestBody UserEventDTO dto) {
        // Llamamos al método para enviar el evento a Kafka.
        producer.enviarEventoUsuario(dto);

        return ResponseEntity.ok("Mensaje enviado a Kafka");
    }
}
