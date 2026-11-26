package cl.duoc.cocinacapital.usuarios.dto;

import cl.duoc.cocinacapital.usuarios.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTOs (records) que entran y salen por la API. La respuesta nunca incluye la clave.
public final class UsuarioDtos {

    private UsuarioDtos() {
    }

    public record RegistroRequest(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
            String nombre,

            @NotBlank(message = "El correo es obligatorio")
            @Email(message = "Correo inválido")
            String correo,

            @NotBlank(message = "La clave es obligatoria")
            @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
            String clave,

            @NotBlank(message = "La comuna es obligatoria")
            String comuna
    ) {
    }

    public record LoginRequest(
            @NotBlank(message = "El correo es obligatorio") String correo,
            @NotBlank(message = "La clave es obligatoria") String clave
    ) {
    }

    public record ActualizarUsuarioRequest(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
            String nombre,

            @NotBlank(message = "La comuna es obligatoria")
            String comuna
    ) {
    }

    public record UsuarioResponse(Long id, String nombre, String correo, String comuna) {
        public static UsuarioResponse desde(Usuario u) {
            return new UsuarioResponse(u.getId(), u.getNombre(), u.getCorreo(), u.getComuna());
        }
    }
}
