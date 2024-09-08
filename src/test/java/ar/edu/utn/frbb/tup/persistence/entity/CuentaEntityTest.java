package ar.edu.utn.frbb.tup.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaEntityTest {

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCuentaEntityFromCuenta() {
        Cliente cliente = new Cliente();
        cliente.setDni(1);
        Cuenta cuenta = new Cuenta();
        cuenta.addMovimiento(new Movimiento());
        cuenta.setBalance(0).setFechaCreacion(LocalDateTime.now()).setMoneda(TipoMoneda.DOLARES).setNumeroCuenta(0)
                .setTipoCuenta(TipoCuenta.CA$).setTitular(cliente);

        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);

        assertEquals(cuenta.getBalance(), cuentaEntity.getBalance());
        assertEquals(cuenta.getFechaCreacion(), cuentaEntity.getFechaCreacion());
        assertEquals(cuenta.getMoneda().getDescription().toUpperCase(), cuentaEntity.getMoneda().toUpperCase());
        assertEquals(cuenta.getNumeroCuenta(), cuentaEntity.getNumeroCuenta());
        assertEquals(cuenta.getTitular().getDni(), cuentaEntity.getTitular());
    }
}
