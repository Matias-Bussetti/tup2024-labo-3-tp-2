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

    private void logicaDeNegocioALaCuentaDestino(Cuenta cuentaDestino, TransferenciaDto transferenciaDto) {
        if (transferenciaDto.getMoneda().equals("pesos") && transferenciaDto.getMonto() >= 1000000) {
            cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto() * 0.98));
        } else if (transferenciaDto.getMoneda().equals("dolares") && transferenciaDto.getMonto() >= 5000) {
            cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto() * 0.95));
        } else {
            cuentaDestino.setBalance(cuentaDestino.getBalance() + (transferenciaDto.getMonto()));
        }

    }

    private TransferenciaResultado cuentaDestinoEnBanco(Cuenta cuentaOrigen, TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {

        Cuenta cuentaDestino = cuentaDao.find(transferenciaDto.getCuentaDestino());

        // Lógica de negocio
        logicaDeNegocioALaCuentaDestino(cuentaDestino, transferenciaDto);

        cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

        TransferenciaResultado transferenciaResultado = new TransferenciaResultado();
        transferenciaResultado.setEstado(TipoEstadoDeTransferencia.EXITOSA)
                .setMensaje("Se realizo la transferencia");

        cuentaService.registrarMovimientoEntrante(cuentaDestino, transferenciaDto);
        cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

        return transferenciaResultado;

        // return new TransferenciaResultado();
    }

    private TransferenciaResultado cuentaDestinoEnOtroBanco(Cuenta cuentaOrigen, TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {
        TransferenciaResultado transferenciaResultado = new TransferenciaResultado();

        if (TipoTransferenciaResultado.OK.equals(banelcoService.transferir(transferenciaDto))) {
            cuentaOrigen.debitarDeCuenta(transferenciaDto.getMonto());

            cuentaService.registrarMovimientoSaliente(cuentaOrigen, transferenciaDto);

            return transferenciaResultado.setMensaje("Se hizo la transferencia")
                    .setEstado(TipoEstadoDeTransferencia.EXITOSA);
        }

        return transferenciaResultado.setMensaje("la cuenta de destino no esta en el banco");

    }

    public TransferenciaResultado transferir(TransferenciaDto transferenciaDto)
            throws NoAlcanzaException, CantidadNegativaException {

        serviceValidator.cuentaExists(transferenciaDto.getCuentaOrigen());
        serviceValidator.cuentaTieneSaldoSuficiente(transferenciaDto.getCuentaOrigen(), transferenciaDto.getMonto());

        Cuenta cuentaOrigen = cuentaDao.find(transferenciaDto.getCuentaOrigen());

        if (cuentaService.find(transferenciaDto.getCuentaDestino()) != null) {
            return cuentaDestinoEnBanco(cuentaOrigen, transferenciaDto);
        } else {
            return cuentaDestinoEnOtroBanco(cuentaOrigen, transferenciaDto);
        }

    }
}
