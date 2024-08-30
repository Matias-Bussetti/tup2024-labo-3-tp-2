package ar.edu.utn.frbb.tup.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceValidatorTest {

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private CuentaDao cuentaDao;

    @InjectMocks
    private ServiceValidator serviceValidator;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testClienteExistsThrowException() {
        when(clienteDao.find(26456437, false)).thenReturn(null);
        when(clienteDao.find(264564370, false)).thenReturn(new Cliente());
        assertThrows(IllegalArgumentException.class, () -> serviceValidator.clienteExists(26456437));

        assertDoesNotThrow(() -> serviceValidator.clienteExists(264564370));

    }

    @Test
    public void testCuentaExistsThrowException() {
        when(cuentaDao.find(26456437, false)).thenReturn(null);
        when(cuentaDao.find(264564370, false)).thenReturn(new Cuenta());
        assertThrows(IllegalArgumentException.class, () -> serviceValidator.cuentaExists(26456437));

        assertDoesNotThrow(() -> serviceValidator.cuentaExists(264564370));

    }

    @Test
    public void testCuentaTieneSaldoSuficienteThrowException() {
        long numeroCuenta = 12345678;
        when(cuentaDao.find(numeroCuenta)).thenReturn(new Cuenta().setBalance(1));
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.cuentaTieneSaldoSuficiente(numeroCuenta, 2));

        assertDoesNotThrow(() -> serviceValidator.cuentaTieneSaldoSuficiente(numeroCuenta, 1));

    }

    @Test
    public void testTitularTieneCuentaConTipoCuentaYMonedaThrowException() {
        Cliente cliente = new Cliente();
        cliente.setDni(123456789);
        cliente.addCuenta(new Cuenta().setTipoCuenta(TipoCuenta.CAU$S).setMoneda(TipoMoneda.DOLARES));

        when(clienteDao.find(123456789, false)).thenReturn(cliente);

        assertThrows(TipoCuentaAlreadyExistsException.class,
                () -> serviceValidator.titularTieneCuentaConTipoCuentaYMoneda(123456789, TipoCuenta.CAU$S,
                        TipoMoneda.DOLARES));

        assertDoesNotThrow(() -> serviceValidator.titularTieneCuentaConTipoCuentaYMoneda(123456789, TipoCuenta.CA$,
                TipoMoneda.PESOS));

    }

    @Test
    public void testclienteEsMayorDeEdadThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.clienteEsMayorDeEdad(17));

        assertDoesNotThrow(() -> serviceValidator.clienteEsMayorDeEdad(18));

    }

    @Test
    public void testClienteAlreadyExistThrowException() {
        when(clienteDao.find(123456789, false)).thenReturn(new Cliente());
        when(clienteDao.find(1234567890, false)).thenReturn(null);

        assertThrows(ClienteAlreadyExistsException.class,
                () -> serviceValidator.clienteAlreadyExist(123456789));

        assertDoesNotThrow(() -> serviceValidator.clienteAlreadyExist(1234567890));
    }

    @Test
    public void testCuentaAlreadyExistThrowException() {
        when(cuentaDao.find(123456789, false)).thenReturn(new Cuenta().setNumeroCuenta(123456789));
        when(cuentaDao.find(1234567890, false)).thenReturn(null);
        assertThrows(CuentaAlreadyExistsException.class,
                () -> serviceValidator.cuentaAlreadyExist(123456789));

        assertDoesNotThrow(() -> serviceValidator.cuentaAlreadyExist(1234567890));

    }

    @Test
    public void testTipoCuentaSoportadasPorElBancoThrowException() {
        assertThrows(TipoCuentaNotSupportedException.class,
                () -> serviceValidator.tipoCuentaSoportadasPorElBanco(TipoCuenta.CAJA_AHORRO));

        assertDoesNotThrow(() -> serviceValidator.tipoCuentaSoportadasPorElBanco(TipoCuenta.CA$));
        assertDoesNotThrow(() -> serviceValidator.tipoCuentaSoportadasPorElBanco(TipoCuenta.CAU$S));
        assertDoesNotThrow(() -> serviceValidator.tipoCuentaSoportadasPorElBanco(TipoCuenta.CC$));

    }

    @Test
    public void testClienteYaTieneUnaCuentaDeEseTipoThrowException() {
        Cliente cliente = new Cliente();
        cliente.setDni(123456789);
        cliente.addCuenta(new Cuenta().setTipoCuenta(TipoCuenta.CAU$S).setMoneda(TipoMoneda.DOLARES));

        when(clienteDao.find(123456789, true)).thenReturn(cliente);
        when(clienteDao.find(1234567890, true)).thenReturn(new Cliente());

        assertThrows(ClienteYaTieneTipoCuentaException.class,
                () -> serviceValidator.clienteYaTieneUnaCuentaDeEseTipo(123456789, TipoCuenta.CAU$S));

        assertDoesNotThrow(() -> serviceValidator.clienteYaTieneUnaCuentaDeEseTipo(123456789, TipoCuenta.CA$));
        assertDoesNotThrow(() -> serviceValidator.clienteYaTieneUnaCuentaDeEseTipo(1234567890, TipoCuenta.CA$));

    }

}
