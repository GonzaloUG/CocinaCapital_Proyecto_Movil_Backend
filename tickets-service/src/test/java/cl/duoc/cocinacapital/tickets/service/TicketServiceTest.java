package cl.duoc.cocinacapital.tickets.service;

import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CompraRequest;
import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CuponRequest;
import cl.duoc.cocinacapital.tickets.exception.ApiException;
import cl.duoc.cocinacapital.tickets.model.Billetera;
import cl.duoc.cocinacapital.tickets.model.Cupon;
import cl.duoc.cocinacapital.tickets.repository.BilleteraRepository;
import cl.duoc.cocinacapital.tickets.repository.CompraRepository;
import cl.duoc.cocinacapital.tickets.repository.CuponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    private static final long AHORA = 1_700_000_000_000L;

    private BilleteraRepository billeteras;
    private CompraRepository compras;
    private CuponRepository cupones;
    private TicketService service;

    @BeforeEach
    void setUp() {
        billeteras = mock(BilleteraRepository.class);
        compras = mock(CompraRepository.class);
        cupones = mock(CuponRepository.class);
        Clock fijo = Clock.fixed(Instant.ofEpochMilli(AHORA), ZoneId.of("America/Santiago"));
        service = new TicketService(billeteras, compras, cupones, fijo);
    }

    @Test
    void comprarSumaAlSaldo() {
        when(billeteras.findById(1L)).thenReturn(Optional.of(new Billetera(1L, 2)));

        assertEquals(5, service.comprar(new CompraRequest(1L, 3)).saldo().intValue());
        verify(compras).save(any());
    }

    @Test
    void saldoDeUsuarioNuevoEsCero() {
        when(billeteras.findById(9L)).thenReturn(Optional.empty());

        assertEquals(0, service.saldo(9L).saldo().intValue());
    }

    @Test
    void crearCuponSinSaldoLanzaConflicto() {
        when(billeteras.findById(1L)).thenReturn(Optional.of(new Billetera(1L, 0)));

        ApiException e = assertThrows(ApiException.class,
                () -> service.crearCupon(new CuponRequest(1L, 2L, "Café Turri", "Postre gratis")));
        assertEquals(HttpStatus.CONFLICT, e.getStatus());
    }

    @Test
    void crearCuponDescuentaUnTicketYGeneraCodigo() {
        Billetera b = new Billetera(1L, 1);
        when(billeteras.findById(1L)).thenReturn(Optional.of(b));
        when(cupones.existsByCodigo(anyString())).thenReturn(false);
        when(cupones.save(any(Cupon.class))).thenAnswer(inv -> inv.getArgument(0));

        Cupon c = service.crearCupon(new CuponRequest(1L, 2L, "Café Turri", "Postre gratis"));

        assertEquals(0, b.getSaldo().intValue());
        assertEquals(6, c.getCodigo().length());
        assertEquals(AHORA, c.getCreadoEn().longValue());
    }

    @Test
    void canjearCuponValidoLoMarcaComoUsado() {
        Cupon c = cupon(AHORA - 1000, false);
        when(cupones.findByCodigo("ABC123")).thenReturn(Optional.of(c));
        when(cupones.save(c)).thenReturn(c);

        assertTrue(service.canjearCupon(" abc123 ").getCanjeado());
    }

    @Test
    void canjearCuponUsadoOVencidoFalla() {
        when(cupones.findByCodigo("USADO1")).thenReturn(Optional.of(cupon(AHORA, true)));
        when(cupones.findByCodigo("VIEJO1")).thenReturn(Optional.of(cupon(AHORA - Cupon.DURACION_MS, false)));

        assertEquals(HttpStatus.CONFLICT,
                assertThrows(ApiException.class, () -> service.canjearCupon("USADO1")).getStatus());
        assertEquals(HttpStatus.GONE,
                assertThrows(ApiException.class, () -> service.canjearCupon("VIEJO1")).getStatus());
    }

    @Test
    void codigoInexistenteLanzaNoEncontrado() {
        when(cupones.findByCodigo("NADA00")).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND,
                assertThrows(ApiException.class, () -> service.buscarCupon("nada00")).getStatus());
    }

    private static Cupon cupon(long creadoEn, boolean canjeado) {
        Cupon c = new Cupon();
        c.setCodigo("ABC123");
        c.setCreadoEn(creadoEn);
        c.setCanjeado(canjeado);
        return c;
    }
}
