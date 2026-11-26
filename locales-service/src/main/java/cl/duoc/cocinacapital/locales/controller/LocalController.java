package cl.duoc.cocinacapital.locales.controller;

import cl.duoc.cocinacapital.locales.dto.LocalRequest;
import cl.duoc.cocinacapital.locales.model.Local;
import cl.duoc.cocinacapital.locales.service.LocalService;
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
@RequestMapping("/api/locales")
public class LocalController {

    private final LocalService service;

    public LocalController(LocalService service) {
        this.service = service;
    }

    // GET /api/locales
    @GetMapping
    public List<Local> listar() {
        return service.listar();
    }

    // GET /api/locales/{id}
    @GetMapping("/{id}")
    public Local buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // POST /api/locales
    @PostMapping
    public ResponseEntity<Local> crear(@Valid @RequestBody LocalRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    // PUT /api/locales/{id}
    @PutMapping("/{id}")
    public Local actualizar(@PathVariable Long id, @Valid @RequestBody LocalRequest req) {
        return service.actualizar(id, req);
    }

    // DELETE /api/locales/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
