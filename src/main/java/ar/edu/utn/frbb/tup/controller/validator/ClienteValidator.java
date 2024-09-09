package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {
    @Autowired
    private DtoValidator dtoValidator;

    public void validate(ClienteDto clienteDto) {
        dtoValidator.propertyNotNull(clienteDto.getApellido(), "apellido");
        dtoValidator.propertyNotNull(clienteDto.getBanco(), "banco");
        dtoValidator.propertyNotNull(clienteDto.getFechaNacimiento(), "fecha nacimiento");
        dtoValidator.propertyNotNull(clienteDto.getNombre(), "nombre");
        dtoValidator.propertyNotNull(clienteDto.getTipoPersona(), "tipo persona");

        dtoValidator.stringNotEmpty(clienteDto.getApellido(), "apellido");
        dtoValidator.stringNotEmpty(clienteDto.getBanco(), "banco");
        dtoValidator.stringNotEmpty(clienteDto.getFechaNacimiento(), "fecha nacimiento");
        dtoValidator.stringNotEmpty(clienteDto.getNombre(), "nombre");
        dtoValidator.stringNotEmpty(clienteDto.getTipoPersona(), "tipo persona");

        dtoValidator.stringIsTipoPersona(clienteDto.getTipoPersona());
        dtoValidator.stringIsDateTime(clienteDto.getFechaNacimiento());
        dtoValidator.dniIsValid(clienteDto.getDni());
    }
}
