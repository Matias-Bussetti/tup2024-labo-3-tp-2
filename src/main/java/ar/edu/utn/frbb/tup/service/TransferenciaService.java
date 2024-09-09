package ar.edu.utn.frbb.tup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.TransferenciaResultado;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaDoesNotExistException;
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

    public void cuentaOrigenRealizarTransferencia(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        Cuenta cuentaOrigen = cuentaDao.find(transferenciaDto.getCuentaOrigen());
        cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());
        cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);
    }

    public void cuentaDestinoRealizarTransferencia(TransferenciaDto transferenciaDto) {
        Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());
        logicaDeNegocioALaCuentaDestino(cuentaDestino, transferenciaDto);
        cuentaService.registrarMovimientoEntrante(cuentaDestino, transferenciaDto);
    }

    public TransferenciaResultado transferir(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException, CuentaDoesNotExistException {

        try {

            serviceValidator.cuentaExists(transferenciaDto.getCuentaOrigen());
            serviceValidator.cuentaTieneSaldoSuficiente(transferenciaDto.getCuentaOrigen(),
                    transferenciaDto.getMonto());
            serviceValidator.cuentaEsDeTipoMoneda(transferenciaDto.getCuentaOrigen(),
                    TipoMoneda.valueOf(transferenciaDto.getMoneda().toUpperCase()));

            cuentaOrigenRealizarTransferencia(transferenciaDto);

            if (cuentaDao.find(transferenciaDto.getCuentaDestino()) != null) {
                serviceValidator.cuentaEsDeTipoMoneda(transferenciaDto.getCuentaDestino(),
                        TipoMoneda.valueOf(transferenciaDto.getMoneda().toUpperCase()));

                cuentaDestinoRealizarTransferencia(transferenciaDto);
                return new TransferenciaResultado(TipoEstadoDeTransferencia.EXITOSA, "Se hizo la transferencia");
            }

            if (TipoTransferenciaResultado.ERROR.equals(banelcoService.transferir(transferenciaDto)))
                throw new Exception("El servicio de transferencia a otra cuenta fallo");

            return new TransferenciaResultado(TipoEstadoDeTransferencia.EXITOSA, "Se hizo la transferencia");

        } catch (Exception e) {
            return new TransferenciaResultado(TipoEstadoDeTransferencia.FALLIDA, e.getMessage());
        }

    }

    public TransferenciaResultado recibirTransferencia(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException, CuentaDoesNotExistException {

        try {
            serviceValidator.cuentaExists(transferenciaDto.getCuentaDestino());

            serviceValidator.cuentaEsDeTipoMoneda(transferenciaDto.getCuentaDestino(),
                    TipoMoneda.valueOf(transferenciaDto.getMoneda().toUpperCase()));

            cuentaDestinoRealizarTransferencia(transferenciaDto);

            return new TransferenciaResultado().setEstado(TipoEstadoDeTransferencia.EXITOSA)
                    .setMensaje("Se realizo la transferencia");

        } catch (Exception e) {
            return new TransferenciaResultado(TipoEstadoDeTransferencia.FALLIDA, e.getMessage());
        }
    }
}
