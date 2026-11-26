package cl.duoc.cocinacapital.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Tabla "usuarios": la clave nunca se guarda en texto plano, solo su hash SHA-256
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @Column(name = "clave_hash", nullable = false, length = 64)
    private String claveHash;

    @Column(nullable = false, length = 40)
    private String comuna;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String correo, String claveHash, String comuna) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.claveHash = claveHash;
        this.comuna = comuna;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
}
