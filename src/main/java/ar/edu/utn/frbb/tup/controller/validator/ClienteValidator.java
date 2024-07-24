package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.TipoPersona;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto) {
        // System.out.println(TipoPersona.PERSONA_FISICA.getDescripcion() + " = " +
        // clienteDto.getTipoPersona());
        // System.out.println(TipoPersona.PERSONA_FISICA.getDescripcion().equals(clienteDto.getTipoPersona()));
        // System.out.println(TipoPersona.PERSONA_JURIDICA.getDescripcion() + " = " +
        // clienteDto.getTipoPersona());
        // System.out.println(TipoPersona.PERSONA_JURIDICA.getDescripcion().equals(clienteDto.getTipoPersona()));
        // System.out.println(!(TipoPersona.PERSONA_FISICA.getDescripcion().equals(clienteDto.getTipoPersona())
        // ||
        // TipoPersona.PERSONA_JURIDICA.getDescripcion().equals(clienteDto.getTipoPersona())));

        if (!(TipoPersona.PERSONA_FISICA.getDescripcion().equals(clienteDto.getTipoPersona())
                || TipoPersona.PERSONA_JURIDICA.getDescripcion().equals(clienteDto.getTipoPersona()))) {
            throw new IllegalArgumentException("El tipo de persona no es correcto");
        }
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
    }
}
