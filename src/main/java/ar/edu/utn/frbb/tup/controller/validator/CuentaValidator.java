package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;

@Component
public class CuentaValidator {
    @Autowired
    private DtoValidator dtoValidator;

    public void validate(CuentaDto cuentaDto, long dni) {

        dtoValidator.propertyNotNull(cuentaDto.getMoneda(), "moneda");
        dtoValidator.propertyNotNull(cuentaDto.getTipoCuenta(), "Tipo Cuenta");

        dtoValidator.stringNotEmpty(cuentaDto.getMoneda(), "moneda");
        dtoValidator.stringNotEmpty(cuentaDto.getTipoCuenta(), "Tipo Cuenta");

        dtoValidator.stringIsTipoCuenta(cuentaDto.getTipoCuenta());
        dtoValidator.stringIsTipoMoneda(cuentaDto.getMoneda());
        dtoValidator.dniIsValid(dni);
    }
}
