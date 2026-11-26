package cl.duoc.cocinacapital.tickets.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class TicketDtos {

    private TicketDtos() {
    }

    // Compra de 1 a 10 tickets (pago simulado)
    public record CompraRequest(
            @NotNull(message = "El usuario es obligatorio") Long usuarioId,
            @NotNull(message = "La cantidad es obligatoria")
            @Min(value = 1, message = "Puedes comprar entre 1 y 10 tickets")
            @Max(value = 10, message = "Puedes comprar entre 1 y 10 tickets")
            Integer cantidad
    ) {
    }

    // Canje de un ticket en un local: genera un cupón
    public record CuponRequest(
            @NotNull(message = "El usuario es obligatorio") Long usuarioId,
            @NotNull(message = "El local es obligatorio") Long localId,
            @NotBlank(message = "El nombre del local es obligatorio") String localNombre,
            @NotBlank(message = "El beneficio es obligatorio") String beneficio
    ) {
    }

    public record SaldoResponse(Long usuarioId, Integer saldo) {
    }
}
