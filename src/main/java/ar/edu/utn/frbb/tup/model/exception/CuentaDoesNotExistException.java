package ar.edu.utn.frbb.tup.model.exception;

public class CuentaDoesNotExistException extends Throwable {
    public CuentaDoesNotExistException(String message) {
        super(message);
    }
}