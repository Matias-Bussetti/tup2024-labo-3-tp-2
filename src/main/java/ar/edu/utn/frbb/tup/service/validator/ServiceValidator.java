package ar.edu.utn.frbb.tup.service.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteYaTieneTipoCuentaException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.ClienteService;

@Component
public class ServiceValidator {
    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteDao clienteDao;

    public void clienteExists(long dni) {
        if (clienteDao.find(dni, false) == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
    }

    public void cuentaExists(long numeroCuenta) {
        if (cuentaDao.find(numeroCuenta, false) == null) {
            throw new IllegalArgumentException("La cuenta no existe");
        }
    }

    public void cuentaTieneSaldoSuficiente(long numeroCuenta, double monto) {
        if (cuentaDao.find(numeroCuenta).getBalance() < monto) {
            throw new IllegalArgumentException("La cuenta de origen no tiene sufuciente Balance");
        }
    }

    public void titularTieneCuentaConTipoCuentaYMoneda(long dniTitular, TipoCuenta tipoCuenta, TipoMoneda tipoMoneda)
            throws TipoCuentaAlreadyExistsException {
        if (clienteDao.find(dniTitular, false).tieneCuenta(tipoCuenta, tipoMoneda)) {
            throw new TipoCuentaAlreadyExistsException("El cliente ya posee una cuenta de ese tipo y moneda");
        }
    }

    public void clienteEsMayorDeEdad(int edad) {
        if (edad < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }
    }

    public void clienteAlreadyExist(long dni) throws ClienteAlreadyExistsException {

        if (clienteDao.find(dni, false) != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + dni);
        }
    }

    public void cuentaAlreadyExist(long numeroCuenta) throws CuentaAlreadyExistsException {

        if (cuentaDao.find(numeroCuenta, false) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + numeroCuenta + " ya existe.");
        }
    }

    public void tipoCuentaSoportadasPorElBanco(TipoCuenta tipoCuenta) throws TipoCuentaNotSupportedException {
        if (!(tipoCuenta.equals(TipoCuenta.CA$) ||
                tipoCuenta.equals(TipoCuenta.CC$) ||
                tipoCuenta.equals(TipoCuenta.CAU$S))) {
            throw new TipoCuentaNotSupportedException("El tipo cuenta no es soportado");
        }
    }

    public void clienteYaTieneUnaCuentaDeEseTipo(long dniTitular, TipoCuenta tipoCuenta)
            throws ClienteYaTieneTipoCuentaException {
        Set<Cuenta> cuentas = clienteDao.find(dniTitular, true).getCuentas();
        if (cuentas.size() > 0) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getTipoCuenta().equals(tipoCuenta)) {
                    throw new ClienteYaTieneTipoCuentaException("Cliente ya tiene este tipo de cuenta");
                }
            }
        }
    }
}
