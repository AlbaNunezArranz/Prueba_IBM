package es.ibm.usermanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Esta clase se usa para configurar el Swagger.
@Configuration
public class SwaggerConfig {
    @Bean
    // Este método devuelve una instancia OpenAPI que se usará para el Swagger.
    public OpenAPI apiInfo() {
        // Configura la información básica de la API
        return new OpenAPI()
                // Se configura la información básica de la API
                .info(new Info().title("User API")
                        .version("1.0").
                        description("API for managing users"));
    }
}