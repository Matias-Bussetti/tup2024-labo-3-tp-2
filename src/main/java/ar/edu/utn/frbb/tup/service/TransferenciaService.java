package ar.edu.utn.frbb.tup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.tipos.TipoEstadoDeTransferencia;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoTransferenciaResultado;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientoDao;

@Component
public class TransferenciaService {
    CuentaDao cuentaDao = new CuentaDao();
    MovimientoDao movimientoDao = new MovimientoDao();

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private BanelcoService banelcoService;

    public TransferenciaResultado transferir(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {

        // TODO SEPARAR LA FUNCION EN 2 FUNCIONES, UNA DONDE EL CLIENTE DESTINO NO ESTA
        // Y OTRA EN LA QUE SI
        Cuenta cuentaOrigen = cuentaDao.find(transferenciaDto.getCuentaOrigen());

        if (cuentaService.find(transferenciaDto.getCuentaDestino()) != null) {
            // + Si la cuenta destino esta en el database
            Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());

            if (cuentaOrigen.getBalance() < transferenciaDto.getMonto()) {
                throw new IllegalArgumentException("La cuenta de origen no tiene sufuciente Balance");
            }

            // Lógica de negocio
            if (transferenciaDto.getMoneda().equals("pesos") && transferenciaDto.getMonto() >= 1000000) {
                cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto() * 0.98));
            } else if (transferenciaDto.getMoneda().equals("dolares") && transferenciaDto.getMonto() >= 5000) {
                cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto() * 0.95));
            } else {
                cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto()));
            }

            cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

            TransferenciaResultado transferenciaResultado = new TransferenciaResultado();
            transferenciaResultado.setEstado(TipoEstadoDeTransferencia.EXITOSA)
                    .setMensaje("Se realizo la transferencia");

            cuentaService.registrarMovimientoEntrante(cuentaDestino, transferenciaDto);
            cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

            return transferenciaResultado;
        }

        // + Si la cuenta destino no esta en el database
        TransferenciaResultado transferenciaResultado = new TransferenciaResultado();

        if (TipoTransferenciaResultado.OK.equals(banelcoService.transferir(transferenciaDto))) {
            System.out.println("ACA!!!");
            cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

            transferenciaResultado.setMensaje("Se hizo la transferencia");

            cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

        } else {
            transferenciaResultado.setMensaje("la cuenta de destino no esta en el banco");
        }

        transferenciaResultado.setEstado(TipoEstadoDeTransferencia.EXITOSA);
        return transferenciaResultado;

    }
}
