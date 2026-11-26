package cl.duoc.cocinacapital.usuarios.exception;

import org.springframework.http.HttpStatus;

// Error de negocio con su código HTTP (404, 409, 401...)
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String mensaje) {
        super(mensaje);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
