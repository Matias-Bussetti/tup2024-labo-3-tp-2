package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;

@Component
public class ExternalTransferenciaService {
    public TransferenciaResultado transferencia(TransferenciaDto transferenciaDto) {
        return new TransferenciaResultado();
    }
}
