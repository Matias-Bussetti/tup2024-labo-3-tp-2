package ar.edu.utn.frbb.tup.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<String> handleClienteAlreadyExistsException(ClienteAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TipoCuentaNotSupportedException.class)
    public ResponseEntity<String> handleTipoCuentaNotSupportedException(TipoCuentaNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CantidadNegativaException.class)
    public ResponseEntity<String> handleCantidadNegativaException(CantidadNegativaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteYaTieneTipoCuentaException.class)
    public ResponseEntity<String> handleClienteYaTieneTipoCuentaException(ClienteYaTieneTipoCuentaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CuentaAlreadyExistsException.class)
    public ResponseEntity<String> handleCuentaAlreadyExistsException(CuentaAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}