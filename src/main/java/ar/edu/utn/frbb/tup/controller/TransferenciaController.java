package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.service.ExternalTransferenciaService;
import ar.edu.utn.frbb.tup.service.TransferenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferenciaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ExternalTransferenciaService externalTransferenciaService;

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @PostMapping
    @CrossOrigin(origins = "*")
    public TransferenciaResultado transferir(@RequestBody TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        transferenciaValidator.validate(transferenciaDto);

        if (cuentaService.find(transferenciaDto.getCuentaOrigen()) == null) {
            throw new IllegalArgumentException("La cuenta de origen no existe");
        }

        TransferenciaResultado transferenciaResultado;

        if (cuentaService.find(transferenciaDto.getCuentaDestino()) != null) {
            transferenciaResultado = transferenciaService.transferencia(transferenciaDto);
            transferenciaResultado.setMensaje("la cuenta de destino esta en el banco");
        } else {
            transferenciaResultado = externalTransferenciaService.transferencia(transferenciaDto);
            transferenciaResultado.setMensaje("la cuenta de destino no esta en el banco");
        }

        System.out.println(transferenciaDto);
        System.out.println(transferenciaResultado);
        return transferenciaResultado;
    }
}
