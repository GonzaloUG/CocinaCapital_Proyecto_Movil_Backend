package cl.duoc.cocinacapital.tickets.controller;

import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CuponRequest;
import cl.duoc.cocinacapital.tickets.model.Cupon;
import cl.duoc.cocinacapital.tickets.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    private final TicketService service;

    public CuponController(TicketService service) {
        this.service = service;
    }

    // POST /api/cupones -> canjea 1 ticket en un local y crea el cupón
    @PostMapping
    public ResponseEntity<Cupon> crear(@Valid @RequestBody CuponRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearCupon(req));
    }

    // GET /api/cupones/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public List<Cupon> deUsuario(@PathVariable Long usuarioId) {
        return service.cuponesDe(usuarioId);
    }

    // GET /api/cupones/{codigo}
    @GetMapping("/{codigo}")
    public Cupon buscar(@PathVariable String codigo) {
        return service.buscarCupon(codigo);
    }

    // PUT /api/cupones/{codigo}/canjear -> el comercio marca el cupón como usado
    @PutMapping("/{codigo}/canjear")
    public Cupon canjear(@PathVariable String codigo) {
        return service.canjearCupon(codigo);
    }

    // DELETE /api/cupones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCupon(id);
        return ResponseEntity.noContent().build();
    }
}
