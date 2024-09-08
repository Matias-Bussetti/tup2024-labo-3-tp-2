package ar.edu.utn.frbb.tup.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.tipos.TipoMovimiento;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovimientoEntityTest {

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMovimientoEntityFromMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(new Cuenta().setNumeroCuenta(0));
        movimiento.setDescripcionBreve("a");
        movimiento.setFechaCreacion(LocalDateTime.now());
        movimiento.setId(0);
        movimiento.setMonto(0);
        movimiento.setTipo(TipoMovimiento.TRANSFERENCIA);

        MovimientoEntity movimientoEntity = new MovimientoEntity(movimiento);

        assertEquals(movimiento.getCuenta().getNumeroCuenta(), movimientoEntity.getCuenta());
        assertEquals(movimiento.getDescripcionBreve(), movimientoEntity.getDescripcionBreve());
        assertEquals(movimiento.getFechaCreacion(), movimientoEntity.getFechaCreacion());
        assertEquals(movimiento.getId(), movimientoEntity.getId());
        assertEquals(movimiento.getTipo(), TipoMovimiento.valueOf(movimientoEntity.getTipo()));
    }

    @Test
    public void testMovimientoEntityToMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(new Cuenta().setNumeroCuenta(0));
        movimiento.setDescripcionBreve("a");
        movimiento.setFechaCreacion(LocalDateTime.now());
        movimiento.setId(0);
        movimiento.setMonto(0);
        movimiento.setTipo(TipoMovimiento.TRANSFERENCIA);

        MovimientoEntity movimientoEntity = new MovimientoEntity(movimiento);

        assertEquals(movimiento.getDescripcionBreve(), movimientoEntity.toMovimiento().getDescripcionBreve());
        assertEquals(movimiento.getFechaCreacion(), movimientoEntity.toMovimiento().getFechaCreacion());
        assertEquals(movimiento.getId(), movimientoEntity.toMovimiento().getId());
    }
}
