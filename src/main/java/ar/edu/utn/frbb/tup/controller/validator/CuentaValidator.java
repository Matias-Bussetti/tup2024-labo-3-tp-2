package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;

@Component
public class CuentaValidator {
    @Autowired
    private DtoValidator dtoValidator;

    public void validate(CuentaDto cuentaDto, long dni) {

        dtoValidator.stringIsTipoCuenta(cuentaDto.getTipoCuenta());
        dtoValidator.stringIsTipoMoneda(cuentaDto.getMoneda());
        dtoValidator.dniIsValid(dni);
    }
}
