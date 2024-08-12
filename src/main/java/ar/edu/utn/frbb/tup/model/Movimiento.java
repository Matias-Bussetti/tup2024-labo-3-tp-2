package ar.edu.utn.frbb.tup.model;

import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;

public class Movimiento {
    private long id;
    private LocalDateTime fechaCreacion;
    private double balance;
    private long cuentaDestino;
    private TipoMoneda moneda;

    @JsonBackReference // Estas anotaciones de Jackson pueden ayudarte a romper la referencia circular.
    private Cuenta cuenta;

    public Movimiento() {
        this.id = new Random().nextLong();
        this.fechaCreacion = LocalDateTime.now();
    }

    public long getId() {
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

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

}
