package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CuentaService {
    CuentaDao cuentaDao = new CuentaDao();

    @Autowired
    ClienteService clienteService;

    // Generar casos de test para darDeAltaCuenta
    // 1 - cuenta existente
    // 2 - cuenta no soportada
    // 3 - cliente ya tiene cuenta de ese tipo //? Calculo que tengo que añadir esto
    // 4 - cuenta creada exitosamente
    public void darDeAltaCuenta(Cuenta cuenta, long dniTitular)
            throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, TipoCuentaNotSupportedException,
            ClienteYaTieneTipoCuentaException {

        if (cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        // Chequear cuentas soportadas por el banco CA$ CC$ CAU$S
        // if (!tipoCuentaEstaSoportada(cuenta)) {...}
        if (!(cuenta.getTipoCuenta().equals(TipoCuenta.CA$) ||
                cuenta.getTipoCuenta().equals(TipoCuenta.CC$) ||
                cuenta.getTipoCuenta().equals(TipoCuenta.CAU$S))) {
            throw new TipoCuentaNotSupportedException("El tipo cuenta no es soportado");
        }

        if (clienteService.buscarClientePorDni(dniTitular).getCuentas().size() > 0) {
            for (Cuenta cuentaCliente : clienteService.buscarClientePorDni(dniTitular).getCuentas()) {
                if (cuentaCliente.getTipoCuenta().equals(cuenta.getTipoCuenta())) {
                    throw new ClienteYaTieneTipoCuentaException("Cliente ya tiene este tipo de cuenta");
                }
            }
        }

        clienteService.agregarCuenta(cuenta, dniTitular);
        cuentaDao.save(cuenta);

    }

    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }
}
