package cl.duoc.cocinacapital.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Microservicio 1: usuarios (registro, login y perfil). Puerto 8081.
@SpringBootApplication
public class UsuariosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServiceApplication.class, args);
    }
}
