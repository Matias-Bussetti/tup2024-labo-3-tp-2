package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.entity.ClienteEntity;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;
import ar.edu.utn.frbb.tup.persistence.entity.MovimientoEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovimientoDao extends AbstractBaseDao {
    @Override
    protected String getEntityName() {
        return "MOVIMIENTO";
    }

    public void save(Movimiento movimiento) {
        MovimientoEntity entity = new MovimientoEntity(movimiento);
        getInMemoryDatabase().put(entity.getId(), entity);
    }

    public Movimiento find(long id) {

        if (getInMemoryDatabase().get(id) == null) {
            return null;
        }
        return ((MovimientoEntity) getInMemoryDatabase().get(id)).toMovimiento();
    }

    public List<Movimiento> getMovimientosByCuenta(long numeroCuenta) {
        List<Movimiento> moviemientosDeLaCuenta = new ArrayList<>();
        for (Object object : getInMemoryDatabase().values()) {
            MovimientoEntity movimiento = ((MovimientoEntity) object);
            if (movimiento.getCuenta() == (numeroCuenta)) {
                moviemientosDeLaCuenta.add(movimiento.toMovimiento());
            }
        }
        return moviemientosDeLaCuenta;
    }
}
