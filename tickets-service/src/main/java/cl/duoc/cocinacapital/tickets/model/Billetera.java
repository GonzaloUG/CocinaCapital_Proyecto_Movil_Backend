package cl.duoc.cocinacapital.tickets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Tabla "billeteras": saldo de tickets de cada usuario (usuarioId viene de usuarios-service)
@Entity
@Table(name = "billeteras")
public class Billetera {

    @Id
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(nullable = false)
    private Integer saldo = 0;

    public Billetera() {
    }

    public Billetera(Long usuarioId, Integer saldo) {
        this.usuarioId = usuarioId;
        this.saldo = saldo;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getSaldo() { return saldo; }
    public void setSaldo(Integer saldo) { this.saldo = saldo; }
}
