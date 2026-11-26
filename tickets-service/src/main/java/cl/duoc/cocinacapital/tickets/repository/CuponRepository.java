package cl.duoc.cocinacapital.tickets.repository;

import cl.duoc.cocinacapital.tickets.model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Long> {

    Optional<Cupon> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Cupon> findByUsuarioIdOrderByCreadoEnDesc(Long usuarioId);

    void deleteByUsuarioId(Long usuarioId);
}
