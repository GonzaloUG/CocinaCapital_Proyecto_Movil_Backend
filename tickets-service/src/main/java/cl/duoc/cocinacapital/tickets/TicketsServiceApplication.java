package cl.duoc.cocinacapital.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

// Microservicio 3: tickets (saldo y compras) y cupones (canje). Puerto 8083.
@SpringBootApplication
public class TicketsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsServiceApplication.class, args);
    }

    // Reloj inyectable: en las pruebas se reemplaza por uno fijo
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
