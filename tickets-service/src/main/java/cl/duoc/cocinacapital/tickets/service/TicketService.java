package cl.duoc.cocinacapital.tickets.service;

import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CompraRequest;
import cl.duoc.cocinacapital.tickets.dto.TicketDtos.CuponRequest;
import cl.duoc.cocinacapital.tickets.dto.TicketDtos.SaldoResponse;
import cl.duoc.cocinacapital.tickets.exception.ApiException;
import cl.duoc.cocinacapital.tickets.model.Billetera;
import cl.duoc.cocinacapital.tickets.model.Compra;
import cl.duoc.cocinacapital.tickets.model.Cupon;
import cl.duoc.cocinacapital.tickets.repository.BilleteraRepository;
import cl.duoc.cocinacapital.tickets.repository.CompraRepository;
import cl.duoc.cocinacapital.tickets.repository.CuponRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.List;

@Service
public class TicketService {

    public static final int PRECIO_TICKET = 3990;
    private static final String CARACTERES = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private final BilleteraRepository billeteras;
    private final CompraRepository compras;
    private final CuponRepository cupones;
    private final Clock clock;
    private final SecureRandom random = new SecureRandom();

    public TicketService(BilleteraRepository billeteras, CompraRepository compras,
                         CuponRepository cupones, Clock clock) {
        this.billeteras = billeteras;
        this.compras = compras;
        this.cupones = cupones;
        this.clock = clock;
    }

    // ---------- Tickets ----------

    public SaldoResponse saldo(Long usuarioId) {
        return new SaldoResponse(usuarioId, billetera(usuarioId).getSaldo());
    }

    @Transactional
    public SaldoResponse comprar(CompraRequest req) {
        Billetera b = billetera(req.usuarioId());
        b.setSaldo(b.getSaldo() + req.cantidad());
        billeteras.save(b);
        compras.save(new Compra(null, req.usuarioId(), req.cantidad(),
                req.cantidad() * PRECIO_TICKET, clock.millis()));
        return new SaldoResponse(req.usuarioId(), b.getSaldo());
    }

    public List<Compra> historial(Long usuarioId) {
        return compras.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    // Borra todo lo del usuario (se llama cuando elimina su cuenta)
    @Transactional
    public void eliminarDatosUsuario(Long usuarioId) {
        cupones.deleteByUsuarioId(usuarioId);
        compras.deleteByUsuarioId(usuarioId);
        billeteras.deleteById(usuarioId);
    }

    // ---------- Cupones ----------

    // RN-01: canjear un ticket descuenta 1 del saldo y genera un cupón único
    @Transactional
    public Cupon crearCupon(CuponRequest req) {
        Billetera b = billetera(req.usuarioId());
        if (b.getSaldo() <= 0) {
            throw new ApiException(HttpStatus.CONFLICT, "No tienes tickets. Compra en la pestaña Tickets.");
        }
        b.setSaldo(b.getSaldo() - 1);
        billeteras.save(b);

        Cupon cupon = new Cupon();
        cupon.setCodigo(codigoUnico());
        cupon.setUsuarioId(req.usuarioId());
        cupon.setLocalId(req.localId());
        cupon.setLocalNombre(req.localNombre());
        cupon.setBeneficio(req.beneficio());
        cupon.setCreadoEn(clock.millis());
        cupon.setCanjeado(false);
        return cupones.save(cupon);
    }

    public List<Cupon> cuponesDe(Long usuarioId) {
        return cupones.findByUsuarioIdOrderByCreadoEnDesc(usuarioId);
    }

    public Cupon buscarCupon(String codigo) {
        return cupones.findByCodigo(normalizar(codigo))
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "El código " + normalizar(codigo) + " no existe"));
    }

    // El comercio valida el cupón (escanea el QR o escribe el código)
    @Transactional
    public Cupon canjearCupon(String codigo) {
        Cupon cupon = buscarCupon(codigo);
        if (Boolean.TRUE.equals(cupon.getCanjeado())) {
            throw new ApiException(HttpStatus.CONFLICT, "Este cupón ya fue canjeado");
        }
        if (cupon.vencido(clock.millis())) {
            throw new ApiException(HttpStatus.GONE, "Este cupón venció");
        }
        cupon.setCanjeado(true);
        return cupones.save(cupon);
    }

    public void eliminarCupon(Long id) {
        Cupon cupon = cupones.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
        cupones.delete(cupon);
    }

    // ---------- Apoyo ----------

    private Billetera billetera(Long usuarioId) {
        return billeteras.findById(usuarioId).orElseGet(() -> new Billetera(usuarioId, 0));
    }

    private String codigoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
            }
            codigo = sb.toString();
        } while (cupones.existsByCodigo(codigo));
        return codigo;
    }

    private static String normalizar(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase();
    }
}
