package ar.edu.utn.frbb.tup.model;

import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ar.edu.utn.frbb.tup.model.tipos.TipoMovimiento;

public class Movimiento {
    private long id;
    private LocalDateTime fechaCreacion;
    private double monto;
    private String descripcionBreve;
    private TipoMovimiento tipo;

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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

}
