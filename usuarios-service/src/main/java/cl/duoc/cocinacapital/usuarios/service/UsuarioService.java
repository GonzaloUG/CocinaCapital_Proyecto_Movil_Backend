package cl.duoc.cocinacapital.usuarios.service;

import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.ActualizarUsuarioRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.LoginRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.RegistroRequest;
import cl.duoc.cocinacapital.usuarios.dto.UsuarioDtos.UsuarioResponse;
import cl.duoc.cocinacapital.usuarios.exception.ApiException;
import cl.duoc.cocinacapital.usuarios.model.Usuario;
import cl.duoc.cocinacapital.usuarios.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // CREATE: registro de un usuario nuevo
    public UsuarioResponse registrar(RegistroRequest req) {
        String correo = normalizarCorreo(req.correo());
        if (repository.existsByCorreo(correo)) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una cuenta con ese correo");
        }
        Usuario nuevo = new Usuario(null, req.nombre().trim(), correo, hash(req.clave()), req.comuna());
        return UsuarioResponse.desde(repository.save(nuevo));
    }

    // Inicio de sesión: compara el hash de la clave
    public UsuarioResponse login(LoginRequest req) {
        Usuario usuario = repository.findByCorreo(normalizarCorreo(req.correo()))
                .filter(u -> u.getClaveHash().equals(hash(req.clave())))
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));
        return UsuarioResponse.desde(usuario);
    }

    // READ
    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioResponse::desde).toList();
    }

    public UsuarioResponse buscar(Long id) {
        return UsuarioResponse.desde(obtener(id));
    }

    // UPDATE: nombre y comuna (el correo identifica la cuenta y no se cambia)
    public UsuarioResponse actualizar(Long id, ActualizarUsuarioRequest req) {
        Usuario usuario = obtener(id);
        usuario.setNombre(req.nombre().trim());
        usuario.setComuna(req.comuna());
        return UsuarioResponse.desde(repository.save(usuario));
    }

    // DELETE
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Usuario obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private static String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase();
    }

    static String hash(String clave) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(clave.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }
}
