package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteDoesNotExistException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoPersona;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ServiceValidator serviceValidator;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testClienteSuccess() throws ClienteAlreadyExistsException, CuentaAlreadyExistsException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("matias").setApellido("bsstt").setDni(4326).setFechaNacimiento("2002-02-22");
        clienteDto.setBanco("banco").setTipoPersona("F");

        Cliente cliente = clienteService.darDeAltaCliente(clienteDto);
        verify(clienteDao, times(1)).save(cliente);
    }

    @Test
    public void testAgregarCuentaSuccess() throws TipoCuentaAlreadyExistsException, ClienteDoesNotExistException {
        Cliente cliente = new Cliente();
        cliente.setNombre("matias").setApellido("bsstt").setDni(4326).setFechaNacimiento(LocalDate.of(2002, 1, 1));
        cliente.setBanco("banco").setTipoPersona("F");

        when(clienteDao.find(4326, true)).thenReturn(cliente);

        Cuenta cuenta = new Cuenta();
        clienteService.agregarCuenta(cuenta, 4326);
        assertEquals(cliente.getCuentas().size(), 1);
    }

}