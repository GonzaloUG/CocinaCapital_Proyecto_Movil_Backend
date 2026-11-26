package cl.duoc.cocinacapital.locales.service;

import cl.duoc.cocinacapital.locales.dto.LocalRequest;
import cl.duoc.cocinacapital.locales.exception.ApiException;
import cl.duoc.cocinacapital.locales.model.Local;
import cl.duoc.cocinacapital.locales.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LocalServiceTest {

    private LocalRepository repository;
    private LocalService service;

    private final LocalRequest request =
            new LocalRequest(" Café Turri ", "Valparaíso", "Chilena", "Postre gratis", -33.04, -71.62);

    @BeforeEach
    void setUp() {
        repository = mock(LocalRepository.class);
        service = new LocalService(repository);
    }

    @Test
    void crearCopiaLosDatosYGuarda() {
        when(repository.save(any(Local.class))).thenAnswer(inv -> inv.getArgument(0));

        Local creado = service.crear(request);

        assertEquals("Café Turri", creado.getNombre());
        assertEquals(-33.04, creado.getLatitud(), 0.0001);
    }

    @Test
    void listarDevuelveTodos() {
        when(repository.findAll()).thenReturn(List.of(new Local(), new Local()));

        assertEquals(2, service.listar().size());
    }

    @Test
    void actualizarYEliminarLocalExistente() {
        Local existente = new Local(1L, "Viejo", "Concón", "Chilena", "Nada", 0.0, 0.0);
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        assertEquals("Café Turri", service.actualizar(1L, request).getNombre());

        service.eliminar(1L);
        verify(repository).delete(existente);
    }

    @Test
    void buscarInexistenteLanzaNoEncontrado() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        ApiException e = assertThrows(ApiException.class, () -> service.buscar(7L));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }
}
