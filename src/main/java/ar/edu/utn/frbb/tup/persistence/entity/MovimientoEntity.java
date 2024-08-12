package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class MovimientoEntity extends BaseEntity {

    long id;
    LocalDateTime fechaCreacion;
    double balance;
    Long cuenta;
    long cuentaDestino;
    String moneda;

    public MovimientoEntity(Movimiento movimiento) {
        super(movimiento.getId());
        this.id = movimiento.getId();
        this.balance = movimiento.getBalance();
        this.cuenta = movimiento.getCuenta().getNumeroCuenta();
        this.moneda = movimiento.getMoneda().toString();
        this.cuentaDestino = movimiento.getCuentaDestino();
        this.fechaCreacion = movimiento.getFechaCreacion();
    }

    public Movimiento toMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setBalance(this.balance);
        movimiento.setId(this.id);
        movimiento.setCuentaDestino(this.cuentaDestino);
        movimiento.setMoneda(TipoMoneda.valueOf(this.moneda));
        movimiento.setFechaCreacion(this.fechaCreacion);

        if (this.cuenta != null) {
            CuentaDao clienteDao = new CuentaDao();
            movimiento.setCuenta(clienteDao.find(this.cuenta));
        }

        return movimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getCuenta() {
        return cuenta;
    }

    public void setCuenta(long cuenta) {
        this.cuenta = cuenta;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
