package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {
    @Autowired
    private DtoValidator dtoValidator;

    public void validate(ClienteDto clienteDto) {
        dtoValidator.stringIsTipoPersona(clienteDto.getTipoPersona());
        dtoValidator.stringIsDateTime(clienteDto.getFechaNacimiento());
    }
}
