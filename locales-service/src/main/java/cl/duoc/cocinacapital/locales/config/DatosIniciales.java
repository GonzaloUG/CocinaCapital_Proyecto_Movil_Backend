package cl.duoc.cocinacapital.locales.config;

import cl.duoc.cocinacapital.locales.model.Local;
import cl.duoc.cocinacapital.locales.repository.LocalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// Carga los 6 locales de ejemplo la primera vez (si la tabla está vacía)
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarLocales(LocalRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.saveAll(List.of(
                    new Local(null, "Café Turri", "Valparaíso", "Chilena", "Postre gratis", -33.0429, -71.6276),
                    new Local(null, "La Picá del Puerto", "Valparaíso", "Mariscos", "15% de descuento", -33.0380, -71.6290),
                    new Local(null, "Sushi Marga Marga", "Viña del Mar", "Japonesa", "Bebida gratis", -33.0245, -71.5518),
                    new Local(null, "Empanadas Concón", "Concón", "Chilena", "2x1 en empanadas", -32.9230, -71.5170),
                    new Local(null, "Pizzería El Sol", "Quilpué", "Italiana", "10% de descuento", -33.0475, -71.4425),
                    new Local(null, "Sabor Andino", "Villa Alemana", "Peruana", "Entrada gratis", -33.0425, -71.3730)
            ));
        };
    }
}
