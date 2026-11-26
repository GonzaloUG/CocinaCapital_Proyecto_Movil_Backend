package cl.duoc.cocinacapital.locales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Microservicio 2: locales asociados (restaurantes). Puerto 8082.
@SpringBootApplication
public class LocalesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalesServiceApplication.class, args);
    }
}
