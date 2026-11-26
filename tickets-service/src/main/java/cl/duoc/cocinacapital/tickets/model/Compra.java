package cl.duoc.cocinacapital.tickets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Tabla "compras": historial de compras de tickets (pago simulado)
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer total;

    // Fecha en milisegundos (epoch), igual que en la app
    @Column(nullable = false)
    private Long fecha;

    public Compra() {
    }

    public Compra(Long id, Long usuarioId, Integer cantidad, Integer total, Long fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    public Long getFecha() { return fecha; }
    public void setFecha(Long fecha) { this.fecha = fecha; }
}
