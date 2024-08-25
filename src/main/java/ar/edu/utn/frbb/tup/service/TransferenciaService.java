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
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

@Component
public class TransferenciaService {
    CuentaDao cuentaDao = new CuentaDao();
    MovimientoDao movimientoDao = new MovimientoDao();

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private BanelcoService banelcoService;

    @Autowired
    ServiceValidator serviceValidator;

    public void logicaDeNegocioALaCuentaDestino(Cuenta cuentaDestino, TransferenciaDto transferenciaDto) {
        double monto = transferenciaDto.getMonto();

        if ("pesos".equals(transferenciaDto.getMoneda()) && monto >= 1000000) {
            monto = monto * 0.98;
        } else if ("dolares".equals(transferenciaDto.getMoneda()) && monto >= 5000) {
            monto = monto * 0.95;
        }

        cuentaDestino.setBalance(cuentaDestino.getBalance() + monto);
    }

    public TransferenciaResultado transferir(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        TransferenciaResultado transferenciaResultado = new TransferenciaResultado();

        try {

            serviceValidator.cuentaExists(transferenciaDto.getCuentaOrigen());
            serviceValidator.cuentaTieneSaldoSuficiente(transferenciaDto.getCuentaOrigen(),
                    transferenciaDto.getMonto());

            Cuenta cuentaOrigen = cuentaDao.find(transferenciaDto.getCuentaOrigen());
            Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());

            if (cuentaDestino != null) {
                // Lógica de negocio
                logicaDeNegocioALaCuentaDestino(cuentaDestino, transferenciaDto);

                cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

                cuentaService.registrarMovimientoEntrante(cuentaDestino, transferenciaDto);
                cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

                return new TransferenciaResultado().setEstado(TipoEstadoDeTransferencia.EXITOSA)
                        .setMensaje("Se realizo la transferencia");

            } else {
                if (TipoTransferenciaResultado.OK.equals(banelcoService.transferir(transferenciaDto))) {
                    cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

                    cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

                    return transferenciaResultado.setMensaje("Se hizo la transferencia")
                            .setEstado(TipoEstadoDeTransferencia.EXITOSA);
                }
                return transferenciaResultado.setMensaje("El servicio de transferencia a otra cuenta fallo")
                        .setEstado(TipoEstadoDeTransferencia.FALLIDA);

            }

        } catch (Exception e) {
            return transferenciaResultado.setEstado(TipoEstadoDeTransferencia.FALLIDA)
                    .setMensaje(e.getMessage());
        }
    }

    public TransferenciaResultado recibirTransferencia(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {

        try {
            serviceValidator.cuentaExists(transferenciaDto.getCuentaDestino());

            Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());

            logicaDeNegocioALaCuentaDestino(cuentaDestino, transferenciaDto);

            cuentaService.registrarMovimientoEntrante(cuentaDestino, transferenciaDto);

            return new TransferenciaResultado().setEstado(TipoEstadoDeTransferencia.EXITOSA)
                    .setMensaje("Se realizo la transferencia");

        } catch (Exception e) {
            return new TransferenciaResultado().setEstado(TipoEstadoDeTransferencia.FALLIDA)
                    .setMensaje(e.getMessage());
        }
    }
}
