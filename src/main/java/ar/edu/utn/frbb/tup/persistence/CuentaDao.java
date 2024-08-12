package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.entity.ClienteEntity;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CuentaDao extends AbstractBaseDao {

    @Override
    protected String getEntityName() {
        return "CUENTA";
    }

    public void save(Cuenta cuenta) {
        CuentaEntity entity = new CuentaEntity(cuenta);
        getInMemoryDatabase().put(entity.getId(), entity);

        MovimientoDao movimientoDao = new MovimientoDao();

        for (Movimiento movimiento : cuenta.getMovimientos()) {
            movimientoDao.save(movimiento);
        }
    }

    public Cuenta find(long id) {

        if (getInMemoryDatabase().get(id) == null) {
            return null;
        }
        return ((CuentaEntity) getInMemoryDatabase().get(id)).toCuenta();
    }

    public Cuenta find(long id, boolean loadComplete) {

        if (getInMemoryDatabase().get(id) == null)
            return null;
        Cuenta cuenta = ((CuentaEntity) getInMemoryDatabase().get(id)).toCuenta();
        if (loadComplete) {
            MovimientoDao movimientoDao = new MovimientoDao();

            for (Movimiento movimiento : movimientoDao.getMovimientosByCuenta(id)) {
                cuenta.addMovimiento(movimiento);
            }
        }
        return cuenta;
    }

    public List<Cuenta> getCuentasByCliente(long dni) {
        List<Cuenta> cuentasDelCliente = new ArrayList<>();
        for (Object object : getInMemoryDatabase().values()) {
            CuentaEntity cuenta = ((CuentaEntity) object);
            if (cuenta.getTitular().equals(dni)) {
                MovimientoDao movimientoDao = new MovimientoDao();

                Cuenta cuentaToAdd = (cuenta.toCuenta());

                for (Movimiento movimiento : movimientoDao.getMovimientosByCuenta(cuenta.getNumeroCuenta())) {
                    cuentaToAdd.addMovimiento(movimiento);
                }

                cuentasDelCliente.add(cuentaToAdd);
            }
        }
        return cuentasDelCliente;
    }
}
