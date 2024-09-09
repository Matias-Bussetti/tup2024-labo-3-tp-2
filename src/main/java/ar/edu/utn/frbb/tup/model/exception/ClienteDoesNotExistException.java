package ar.edu.utn.frbb.tup.model.exception;

public class ClienteDoesNotExistException extends Throwable {
    public ClienteDoesNotExistException(String message) {
        super(message);
    }
}