package cl.duoc.cocinacapital.tickets.repository;

import cl.duoc.cocinacapital.tickets.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    void deleteByUsuarioId(Long usuarioId);
}
