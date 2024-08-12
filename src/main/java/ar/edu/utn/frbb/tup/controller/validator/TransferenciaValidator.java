package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaValidator {
    @Autowired
    private DtoValidator dtoValidator;

    public void validate(TransferenciaDto transferenciaDto) {
        dtoValidator.stringIsTipoMoneda(transferenciaDto.getMoneda());
        dtoValidator.numberIsBiggerThan(transferenciaDto.getMonto(), 0);
        dtoValidator.longIsDistinctThanLong(transferenciaDto.getCuentaDestino(), transferenciaDto.getCuentaOrigen());
    }

}
