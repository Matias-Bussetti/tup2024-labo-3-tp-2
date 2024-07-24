package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.TipoPersona;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransferenciaValidator {

    public void validate(TransferenciaDto transferenciaDto) {
        if (!(transferenciaDto.getMoneda().equals("dolares") || transferenciaDto.getMoneda().equals("pesos"))) {
            throw new IllegalArgumentException("La moneda debe ser: dolares | pesos");
        }
        if (transferenciaDto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }

        if (transferenciaDto.getCuentaDestino() == transferenciaDto.getCuentaOrigen()) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }
    }
}
