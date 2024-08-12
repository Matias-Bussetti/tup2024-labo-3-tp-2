package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        movimiento.setBalance(transferenciaDto.getMonto());
        movimiento.setCuentaDestino(transferenciaDto.getCuentaDestino());

        if (transferenciaDto.getMoneda().equals("dolares")) {
            movimiento.setMoneda(TipoMoneda.DOLARES);
        } else if (transferenciaDto.getMoneda().equals("pesos")) {
            movimiento.setMoneda(TipoMoneda.PESOS);
        }

        cuentaOrigen.addMovimiento(movimiento);

        // Falla aca
        cuentaDao.save(cuentaOrigen);

        return true;
    }

    public boolean registrarMovimientoEntrante(Cuenta cuentaDestino, TransferenciaDto transferenciaDto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuentaDestino);
        movimiento.setBalance(transferenciaDto.getMonto());
        movimiento.setCuentaDestino(transferenciaDto.getCuentaDestino());
        movimiento.setCuentaDestino(transferenciaDto.getCuentaOrigen());

        if (transferenciaDto.getMoneda().equals("dolares")) {
            movimiento.setMoneda(TipoMoneda.DOLARES);
        } else if (transferenciaDto.getMoneda().equals("pesos")) {
            movimiento.setMoneda(TipoMoneda.PESOS);
        }

        cuentaDestino.addMovimiento(movimiento);

        // Falla aca
        cuentaDao.save(cuentaDestino);

        return true;
    }
}
