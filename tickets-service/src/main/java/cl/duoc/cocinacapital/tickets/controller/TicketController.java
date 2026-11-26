package cl.duoc.cocinacapital.tickets.controller;

import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CompraRequest;
import cl.duoc.cocinacapital.tickets.dto.TicketDtos.SaldoResponse;
import cl.duoc.cocinacapital.tickets.model.Compra;
import cl.duoc.cocinacapital.tickets.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    // GET /api/tickets/saldo/{usuarioId}
    @GetMapping("/saldo/{usuarioId}")
    public SaldoResponse saldo(@PathVariable Long usuarioId) {
        return service.saldo(usuarioId);
    }

    // POST /api/tickets/compras -> devuelve el saldo nuevo
    @PostMapping("/compras")
    public ResponseEntity<SaldoResponse> comprar(@Valid @RequestBody CompraRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.comprar(req));
    }

    // GET /api/tickets/compras/usuario/{usuarioId}
    @GetMapping("/compras/usuario/{usuarioId}")
    public List<Compra> historial(@PathVariable Long usuarioId) {
        return service.historial(usuarioId);
    }

    // DELETE /api/tickets/usuario/{usuarioId} -> borra saldo, compras y cupones del usuario
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> eliminarDatosUsuario(@PathVariable Long usuarioId) {
        service.eliminarDatosUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
