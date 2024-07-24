package ar.edu.utn.frbb.tup.service;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.TipoEstadoDeTransferencia;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

@Component
public class TransferenciaService {
    CuentaDao cuentaDao = new CuentaDao();

    public TransferenciaResultado transferencia(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaOrigen = cuentaDao.find(transferenciaDto.getCuentaOrigen());
        Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());

        if (cuentaOrigen.getBalance() < transferenciaDto.getMonto()) {
            throw new IllegalArgumentException("La cuenta de origen no tiene sufuciente Balance");
        }

        cuentaDestino.setBalance(cuentaDestino.getBalance() + transferenciaDto.getMonto());
        cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

        cuentaDao.save(cuentaOrigen);
        cuentaDao.save(cuentaDestino);

        TransferenciaResultado transferenciaResultado = new TransferenciaResultado();
        transferenciaResultado.setEstado(TipoEstadoDeTransferencia.EXITOSA).setMensaje("Se realizo la transferencia");

        return transferenciaResultado;
    }
}
