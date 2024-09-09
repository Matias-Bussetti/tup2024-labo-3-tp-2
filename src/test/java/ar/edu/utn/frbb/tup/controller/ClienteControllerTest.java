package ar.edu.utn.frbb.tup.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteDoesNotExistException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.validator.ServiceValidator;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteValidator clienteValidator;

    @Mock
    private ServiceValidator serviceValidator;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCrearCliente() throws ClienteAlreadyExistsException, CuentaAlreadyExistsException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellido("A").setNombre("B").setFechaNacimiento("2001-01-11").setDni(10_000_000);
        clienteDto.setBanco("banco").setTipoPersona("h");

        when(clienteService.darDeAltaCliente(clienteDto)).thenReturn(new Cliente(clienteDto));

        Cliente cliente = clienteController.crearCliente(clienteDto);

        Cliente clienteExpected = new Cliente(clienteDto);

        assertEquals(clienteExpected.getApellido(), cliente.getApellido());
        assertEquals(clienteExpected.getBanco(), cliente.getBanco());
        assertEquals(clienteExpected.getDni(), cliente.getDni());
        assertEquals(clienteExpected.getEdad(), cliente.getEdad());
        assertEquals(clienteExpected.getFechaNacimiento(), cliente.getFechaNacimiento());
        assertEquals(clienteExpected.getNombre(), cliente.getNombre());
        assertEquals(clienteExpected.getTipoPersona(), cliente.getTipoPersona());
        assertEquals(clienteExpected.toString(), cliente.toString());

    }

    @Test
    public void testObtenerCliente()
            throws ClienteAlreadyExistsException, CuentaAlreadyExistsException, ClienteDoesNotExistException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellido("A").setNombre("B").setFechaNacimiento("2001-01-11").setDni(10_000_000);
        clienteDto.setBanco("banco").setTipoPersona("h");

        when(clienteService.buscarClientePorDni(10_000_000)).thenReturn(new Cliente(clienteDto));

        Cliente cliente = clienteController.obtenerCliente(10_000_000);

        Cliente clienteExpected = new Cliente(clienteDto);

        assertEquals(clienteExpected.getApellido(), cliente.getApellido());
        assertEquals(clienteExpected.getBanco(), cliente.getBanco());
        assertEquals(clienteExpected.getDni(), cliente.getDni());
        assertEquals(clienteExpected.getEdad(), cliente.getEdad());
        assertEquals(clienteExpected.getFechaNacimiento(), cliente.getFechaNacimiento());
        assertEquals(clienteExpected.getNombre(), cliente.getNombre());
        assertEquals(clienteExpected.getTipoPersona(), cliente.getTipoPersona());
        assertEquals(clienteExpected.toString(), cliente.toString());

    }
}
