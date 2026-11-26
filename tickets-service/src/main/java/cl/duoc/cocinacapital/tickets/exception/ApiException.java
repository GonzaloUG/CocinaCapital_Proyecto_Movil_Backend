package cl.duoc.cocinacapital.tickets.exception;

import org.springframework.http.HttpStatus;

// Error de negocio con su código HTTP
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
