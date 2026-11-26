package cl.duoc.cocinacapital.locales.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Datos para crear o editar un local
public record LocalRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
        String nombre,

        @NotBlank(message = "La comuna es obligatoria")
        String comuna,

        @NotBlank(message = "El tipo de cocina es obligatorio")
        String tipoCocina,

        @NotBlank(message = "El beneficio es obligatorio")
        @Size(max = 80, message = "El beneficio no puede superar 80 caracteres")
        String beneficio,

        @NotNull(message = "La latitud es obligatoria")
        @DecimalMin(value = "-90.0", message = "Latitud inválida")
        @DecimalMax(value = "90.0", message = "Latitud inválida")
        Double latitud,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-180.0", message = "Longitud inválida")
        @DecimalMax(value = "180.0", message = "Longitud inválida")
        Double longitud
) {
}
