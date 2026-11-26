package cl.duoc.cocinacapital.tickets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Tabla "cupones": se crea al canjear un ticket en un local. Dura 1 hora y se usa una sola vez.
@Entity
@Table(name = "cupones")
public class Cupon {

    public static final long DURACION_MS = 60L * 60L * 1000L; // 1 hora

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    private String codigo;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "local_id", nullable = false)
    private Long localId;

    @Column(name = "local_nombre", nullable = false, length = 80)
    private String localNombre;

    @Column(nullable = false, length = 80)
    private String beneficio;

    // Momento de creación en milisegundos (epoch)
    @Column(name = "creado_en", nullable = false)
    private Long creadoEn;

    @Column(nullable = false)
    private Boolean canjeado = false;

    public Cupon() {
    }

    public boolean vencido(long ahora) {
        return ahora >= creadoEn + DURACION_MS;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getLocalId() { return localId; }
    public void setLocalId(Long localId) { this.localId = localId; }

    public String getLocalNombre() { return localNombre; }
    public void setLocalNombre(String localNombre) { this.localNombre = localNombre; }

    public String getBeneficio() { return beneficio; }
    public void setBeneficio(String beneficio) { this.beneficio = beneficio; }

    public Long getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Long creadoEn) { this.creadoEn = creadoEn; }

    public Boolean getCanjeado() { return canjeado; }
    public void setCanjeado(Boolean canjeado) { this.canjeado = canjeado; }
}
