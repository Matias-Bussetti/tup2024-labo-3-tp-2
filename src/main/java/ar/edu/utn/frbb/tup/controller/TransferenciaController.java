package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.CuentaTransferencias;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.service.TransferenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TransferenciaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @PostMapping("/transfer")
    @CrossOrigin(origins = "*")
    public TransferenciaResultado transferir(@RequestBody TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        transferenciaValidator.validate(transferenciaDto);

        return transferenciaService.transferir(transferenciaDto);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/cuenta/{cuentaId}/transacciones")
    public CuentaTransferencias obtenerTransacciones(@PathVariable long cuentaId) {
        Cuenta cuenta = cuentaService.find(cuentaId, true);

        CuentaTransferencias cuentaTransferencias = new CuentaTransferencias(cuenta.getNumeroCuenta());

        for (Movimiento movimiento : cuenta.getMovimientos()) {
            cuentaTransferencias.addMovimientos(movimiento);
        }
        return cuentaTransferencias;

    }
}
