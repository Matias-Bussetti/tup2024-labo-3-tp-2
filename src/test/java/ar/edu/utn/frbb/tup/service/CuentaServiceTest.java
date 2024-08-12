package ar.edu.utn.frbb.tup.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;

//Anotacion para usar mockito
@ExtendWith(MockitoExtension.class)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {

    @Mock // Mock de ClienteService (repositorio)
    private CuentaDao cuentaDao;
    @Mock // Mock de ClienteService (repositorio)
    private ClienteDao clienteDao;
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private CuentaService cuentaService;

    @DisplayName("Ejercicios del TP de testing")

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Anotación para hacer una prueba
    @Test
    public void cuentaExistente() {
        // Creo una cuenta
        Cuenta cuenta = new Cuenta();
        // Digamos que la cuenta tiene este dni
        Long dni = 45000L;
        cuenta.setNumeroCuenta(dni);

        // Entonces hacemos que cuando se ejecute cuentaDao.find
        // simule que al pasarle el dni, nos retorna la cuenta ya creada
        when(cuentaDao.find(dni)).thenReturn(cuenta);

        // Entonces hacemos la prueba de que cuando
        // se ejecuta el método cuentaService.darDeAltaCuenta
        // con la cuenta y dni, como establecimos que cuando se ejecuta find nos
        // devuelve una cuenta
        // entoces tira error. Osea lo que esperamos
        // assertThrows(CuentaAlreadyExistsException.class, () ->
        // cuentaService.darDeAltaCuenta(cuenta, dni));
    }

    // Anotación para hacer una prueba
    @Test
    public void cuentaNoSoportada() {
        // Creo una cuenta, uso lo que ya hice en el anterior test
        Cuenta cuenta = new Cuenta();
        // Digamos que la cuenta tiene este dni
        Long dni = 45000L;
        cuenta.setNumeroCuenta(dni);
        // Setteo un tipo no soportado
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        // Para ver si falla cuando si es soportado ->
        // cuenta.setTipoCuenta(TipoCuenta.CA$);

        // Entonces hacemos que cuando se ejecute cuentaDao.find
        // simule que al pasarle el dni, nos null para poder continuar
        // la ejecucion hasta el checkeo
        when(cuentaDao.find(dni)).thenReturn(null);

        // Entonces hacemos la prueba de que cuando
        // se pasa una cuenta con un tipo no soporto
        // entoces tira error de tipocuentanosupported
        // assertThrows(TipoCuentaNotSupportedException.class, () ->
        // cuentaService.darDeAltaCuenta(cuenta, dni));
    }

    // Anotación para hacer una prueba
    @Test
    public void probarDarAltaCuentaConTipoCuentaQueYaExisteEnLasCuentasDelClienteFalle() {
        // Creo una cuenta, uso lo que ya hice en el anterior test
        Cuenta cuenta1 = new Cuenta();
        Cuenta cuenta2 = new Cuenta();

        // Digamos que la cuenta tiene este dni
        Long dni = 45000L;
        cuenta1.setNumeroCuenta(dni);
        cuenta2.setNumeroCuenta(dni);
        // Setteo el mismo tipo en las dos para que tire error
        cuenta1.setTipoCuenta(TipoCuenta.CA$);
        cuenta1.setMoneda(TipoMoneda.PESOS);

        cuenta2.setMoneda(TipoMoneda.PESOS);
        cuenta2.setTipoCuenta(TipoCuenta.CA$);
        // cuenta2.setTipoCuenta(TipoCuenta.CAU$S);// ! Aaca deberia tirar error

        Cliente cliente = new Cliente(); // Creamos un cliente para simular
        cliente.setDni(dni);
        cliente.addCuenta(cuenta1); // Agregamos la cuenta

        when(cuentaDao.find(dni)).thenReturn(null);// Cualculo que se ejecuta todas las veces?

        // Aca lo que hago es retornar el cliente de arriba para
        // simular la busqueda
        when(clienteService.buscarClientePorDni(dni)).thenReturn(cliente);

        // Entonces hacemos la prueba de que cuando
        // se pasa una cuenta con un tipo no soporto
        // entoces tira error de tipocuentanosupported
        // assertThrows(ClienteYaTieneTipoCuentaException.class, () ->
        // cuentaService.darDeAltaCuenta(cuenta2, dni));
    }

    // Anotación para hacer una prueba
    @Test
    public void cuentaSeAgregaCorrectamente() throws TipoCuentaNotSupportedException, ClienteYaTieneTipoCuentaException,
            CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException {
        // Creo una cuenta, uso lo que ya hice en el anterior test
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CAU$S);

        // Digamos que la cuenta tiene este dni
        Long dni = 45000L;
        cuenta.setNumeroCuenta(dni);

        when(cuentaDao.find(dni)).thenReturn(null);

        // Aca lo que hago es retornar el cliente de arriba para
        // simular la busqueda
        Cliente cliente = new Cliente();
        when(clienteService.buscarClientePorDni(dni)).thenReturn(cliente);

        // doNothing().when(clienteService).agregarCuenta(cuenta, dni);

        // cuentaService.darDeAltaCuenta(cuenta, dni);

        verify(clienteService, times(1)).agregarCuenta(cuenta, dni);
        verify(cuentaDao, times(1)).save(cuenta);
        // assertTrue(cliente.getCuentas().contains(cuenta));

    }
}
