package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.tipos.TipoTransferenciaResultado;

@Component
public class BanelcoService {
    public TipoTransferenciaResultado transferir(TransferenciaDto transferenciaDto) {
        return TipoTransferenciaResultado.OK;
    }
}
