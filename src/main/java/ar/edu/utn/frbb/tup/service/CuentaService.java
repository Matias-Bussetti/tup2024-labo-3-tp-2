package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.CuentaTransferencias;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoMovimiento;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class CuentaService {
    CuentaDao cuentaDao = new CuentaDao();

    @Autowired
    ClienteService clienteService;

    @Autowired
    ServiceValidator serviceValidator;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto, long dniTitular)
            throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, TipoCuentaNotSupportedException,
            ClienteYaTieneTipoCuentaException {

        Cuenta cuenta = new Cuenta(cuentaDto);
        cuenta.setMoneda(cuentaDto.parseTipoMoneda());

        serviceValidator.clienteExists(dniTitular);
        serviceValidator.cuentaAlreadyExist(cuenta.getNumeroCuenta());
        serviceValidator.tipoCuentaSoportadasPorElBanco(cuenta.getTipoCuenta());
        serviceValidator.clienteYaTieneUnaCuentaDeEseTipo(dniTitular, cuenta.getTipoCuenta());

        clienteService.agregarCuenta(cuenta, dniTitular);
        cuentaDao.save(cuenta);

        return cuenta;

    }

    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }

    public Cuenta find(long id, boolean loadComplete) {
        return cuentaDao.find(id, loadComplete);
    }

    public boolean registrarMovimientoSaliente(Cuenta cuentaOrigen, TransferenciaDto transferenciaDto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuentaOrigen);
        movimiento.setMonto(transferenciaDto.getMonto());
        movimiento.setDescripcionBreve("Transferencia Saliente a " + transferenciaDto.getCuentaDestino());
        movimiento.setTipo(TipoMovimiento.TRANSFERENCIA);

        cuentaOrigen.addMovimiento(movimiento);

        cuentaDao.save(cuentaOrigen);

        return true;
    }

    public boolean registrarMovimientoEntrante(Cuenta cuentaDestino, TransferenciaDto transferenciaDto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuentaDestino);
        movimiento.setMonto(transferenciaDto.getMonto());
        movimiento.setDescripcionBreve("Transferencia Entrante de " + transferenciaDto.getCuentaDestino());
        movimiento.setTipo(TipoMovimiento.TRANSFERENCIA);

        cuentaDestino.addMovimiento(movimiento);

        // Falla aca
        cuentaDao.save(cuentaDestino);

        return true;
    }

    public CuentaTransferencias obtenerTransaccionesDeCuenta(long numeroCuenta) {

        serviceValidator.cuentaExists(numeroCuenta);

        Cuenta cuenta = cuentaDao.find(numeroCuenta, true);

        CuentaTransferencias cuentaTransferencias = new CuentaTransferencias(cuenta.getNumeroCuenta());

        for (Movimiento movimiento : cuenta.getMovimientos()) {
            cuentaTransferencias.addMovimientos(movimiento);
        }
        return cuentaTransferencias;

    }
}
