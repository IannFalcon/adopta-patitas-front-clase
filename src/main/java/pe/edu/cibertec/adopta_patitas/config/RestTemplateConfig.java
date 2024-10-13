package pe.edu.cibertec.adopta_patitas.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // NOTA: NUNCA DEFINIR LOS BEAN DENTRO DEL CONTROLLER

    @Bean
    public RestTemplate restTemplateAutenticacion(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081/autenticacion")
                .setConnectTimeout(Duration.ofSeconds(5)) // Tiempo de espera maximo para establecer la conexión
                .setReadTimeout(Duration.ofSeconds(10)) // Tiempo de espera maximo para recibir la respuesta total
                .build();
    }

    @Bean
    public RestTemplate restTemplateAutenticacionAltoProcesamiento(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081/")
                .setReadTimeout(Duration.ofSeconds(80))
                .build();
    }

    @Bean
    public RestTemplate restTemplateFinanzas(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081/finanzas")
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Bean
    public RestTemplate restTemplateReporteria(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081/reporteria")
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }

}