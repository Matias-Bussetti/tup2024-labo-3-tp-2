package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteDoesNotExistException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    ClienteDao clienteDao;

    @Autowired
    ServiceValidator serviceValidator;

    public Cliente darDeAltaCliente(ClienteDto clienteDto)
            throws ClienteAlreadyExistsException, CuentaAlreadyExistsException {
        serviceValidator.clienteAlreadyExist(clienteDto.getDni());

        Cliente cliente = new Cliente(clienteDto);

        serviceValidator.clienteEsMayorDeEdad(cliente.getEdad());

        cliente.setTipoPersona(clienteDto.getTipoPersona());
        cliente.setBanco(clienteDto.getBanco());

        clienteDao.save(cliente);
        return cliente;
    }

    public void agregarCuenta(Cuenta cuenta, long dniTitular)
            throws TipoCuentaAlreadyExistsException, ClienteDoesNotExistException {
        Cliente titular = buscarClientePorDni(dniTitular);
        cuenta.setTitular(titular);

        serviceValidator.titularTieneCuentaConTipoCuentaYMoneda(dniTitular, cuenta.getTipoCuenta(), cuenta.getMoneda());

        titular.addCuenta(cuenta);
        clienteDao.save(titular);
    }

    public Cliente buscarClientePorDni(long dni) throws ClienteDoesNotExistException {
        serviceValidator.clienteExists(dni);
        return clienteDao.find(dni, true);
    }

}
