package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class CuentaEntity extends BaseEntity {

    String nombre;
    LocalDateTime fechaCreacion;
    double balance;
    String tipoCuenta;
    String moneda;
    Long titular;
    long numeroCuenta;
    private List<Long> movimientos;

    public CuentaEntity(Cuenta cuenta) {
        super(cuenta.getNumeroCuenta());
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.balance = cuenta.getBalance();
        this.tipoCuenta = cuenta.getTipoCuenta().toString();
        this.moneda = cuenta.getMoneda().toString();
        this.titular = cuenta.getTitular().getDni();
        this.fechaCreacion = cuenta.getFechaCreacion();
        this.movimientos = new ArrayList<>();

        if (cuenta.getMovimientos() != null && !cuenta.getMovimientos().isEmpty()) {
            for (Movimiento movimiento : cuenta.getMovimientos()) {
                movimientos.add(movimiento.getId());
            }
        }
    }

    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(this.balance);
        cuenta.setNumeroCuenta(this.numeroCuenta);
        cuenta.setTipoCuenta(TipoCuenta.valueOf(this.tipoCuenta));
        cuenta.setMoneda(TipoMoneda.valueOf(this.moneda));
        cuenta.setFechaCreacion(this.fechaCreacion);

        if (this.titular != null) {
            ClienteDao clienteDao = new ClienteDao();
            cuenta.setTitular(clienteDao.find(this.titular, false));
        }

        return cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Long getTitular() {
        return titular;
    }

    public void setTitular(Long titular) {
        this.titular = titular;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
