package cl.duoc.cocinacapital.usuarios.service;

import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.ActualizarUsuarioRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.LoginRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.RegistroRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.UsuarioResponse;
import cl.duoc.cocinacapital.usuarios.exception.ApiException;
import cl.duoc.cocinacapital.usuarios.model.Usuario;
import cl.duoc.cocinacapital.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    private UsuarioRepository repository;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioRepository.class);
        service = new UsuarioService(repository);
    }

    @Test
    void registrarGuardaCorreoNormalizadoYClaveHasheada() {
        when(repository.existsByCorreo("ana@correo.cl")).thenReturn(false);
        when(repository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });

        UsuarioResponse r = service.registrar(new RegistroRequest("Ana", " ANA@correo.cl ", "clave123", "Valparaíso"));

        assertEquals(1L, r.id().longValue());
        assertEquals("ana@correo.cl", r.correo());
    }

    @Test
    void registrarConCorreoRepetidoLanzaConflicto() {
        when(repository.existsByCorreo("ana@correo.cl")).thenReturn(true);

        ApiException e = assertThrows(ApiException.class,
                () -> service.registrar(new RegistroRequest("Ana", "ana@correo.cl", "clave123", "Valparaíso")));

        assertEquals(HttpStatus.CONFLICT, e.getStatus());
    }

    @Test
    void loginConClaveIncorrectaLanzaNoAutorizado() {
        Usuario u = new Usuario(1L, "Ana", "ana@correo.cl", UsuarioService.hash("clave123"), "Valparaíso");
        when(repository.findByCorreo("ana@correo.cl")).thenReturn(Optional.of(u));

        assertEquals("Ana", service.login(new LoginRequest("ana@correo.cl", "clave123")).nombre());
        ApiException e = assertThrows(ApiException.class,
                () -> service.login(new LoginRequest("ana@correo.cl", "otra999")));
        assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
    }

    @Test
    void actualizarYEliminar() {
        Usuario u = new Usuario(1L, "Ana", "ana@correo.cl", "x", "Valparaíso");
        when(repository.findById(1L)).thenReturn(Optional.of(u));
        when(repository.save(u)).thenReturn(u);

        UsuarioResponse r = service.actualizar(1L, new ActualizarUsuarioRequest("Ana María", "Concón"));
        assertEquals("Concón", r.comuna());

        service.eliminar(1L);
        verify(repository).delete(u);
    }

    @Test
    void buscarInexistenteLanzaNoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ApiException e = assertThrows(ApiException.class, () -> service.buscar(99L));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }
}
