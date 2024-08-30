package ar.edu.utn.frbb.tup.service.validator;

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
    public void testClienteExistsThrowIllegalArgumentException() {
        when(clienteDao.find(26456437, false)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> serviceValidator.clienteExists(26456437));
    }

    @Test
    public void testCuentaExistsThrowIllegalArgumentException() {
        when(cuentaDao.find(26456437, false)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> serviceValidator.cuentaExists(26456437));
    }

    @Test
    public void testCuentaTieneSaldoSuficienteThrowIllegalArgumentException() {
        long numeroCuenta = 12345678;
        when(cuentaDao.find(numeroCuenta)).thenReturn(new Cuenta().setBalance(0));
        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.cuentaTieneSaldoSuficiente(numeroCuenta, 1));
    }

    @Test
    public void testTitularTieneCuentaConTipoCuentaYMonedaThrowIllegalArgumentException() {
        Cliente cliente = new Cliente();
        cliente.setDni(123456789);
        cliente.addCuenta(new Cuenta().setTipoCuenta(TipoCuenta.CAU$S).setMoneda(TipoMoneda.DOLARES));

        when(clienteDao.find(123456789, false)).thenReturn(cliente);

        assertThrows(IllegalArgumentException.class,
                () -> serviceValidator.titularTieneCuentaConTipoCuentaYMoneda(123456789, TipoCuenta.CAU$S,
                        TipoMoneda.DOLARES));
    }

}
