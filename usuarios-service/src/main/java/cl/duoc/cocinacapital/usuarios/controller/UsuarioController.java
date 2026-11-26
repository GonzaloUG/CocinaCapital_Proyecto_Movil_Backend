package cl.duoc.cocinacapital.usuarios.controller;

import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.ActualizarUsuarioRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.LoginRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.RegistroRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.UsuarioResponse;
import cl.duoc.cocinacapital.usuarios.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // POST /api/usuarios -> registro
    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req));
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public UsuarioResponse login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }

    // GET /api/usuarios
    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar();
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @Valid @RequestBody ActualizarUsuarioRequest req) {
        return service.actualizar(id, req);
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
