package es.ibm.usermanagement.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import es.ibm.usermanagement.kafka.dto.UserEventDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    // Define un bean para enviar mensajes a un tópico Kafka.
    @Bean
    public ProducerFactory<String, UserEventDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // Configuración del servidor de Kafka al que se va a conectar.
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Define un bean que crea la clase principal para enviar mensajes a Kafka.
    @Bean
    public KafkaTemplate<String, UserEventDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

