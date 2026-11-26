package cl.duoc.cocinacapital.locales.service;

import cl.duoc.cocinacapital.locales.dto.LocalRequest;
import cl.duoc.cocinacapital.locales.exception.ApiException;
import cl.duoc.cocinacapital.locales.model.Local;
import cl.duoc.cocinacapital.locales.repository.LocalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {

    private final LocalRepository repository;

    public LocalService(LocalRepository repository) {
        this.repository = repository;
    }

    public List<Local> listar() {
        return repository.findAll();
    }

    public Local buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Local no encontrado"));
    }

    public Local crear(LocalRequest req) {
        Local local = new Local();
        copiar(req, local);
        return repository.save(local);
    }

    public Local actualizar(Long id, LocalRequest req) {
        Local local = buscar(id);
        copiar(req, local);
        return repository.save(local);
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private static void copiar(LocalRequest req, Local local) {
        local.setNombre(req.nombre().trim());
        local.setComuna(req.comuna());
        local.setTipoCocina(req.tipoCocina().trim());
        local.setBeneficio(req.beneficio().trim());
        local.setLatitud(req.latitud());
        local.setLongitud(req.longitud());
    }
}
