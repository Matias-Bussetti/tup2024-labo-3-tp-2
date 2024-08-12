package ar.edu.utn.frbb.tup.model;

import java.util.HashSet;
import java.util.Set;

public class CuentaTransferencias {
    private long numeroCuenta;
    private Set<Transferencias> movimientos = new HashSet<>();

    public CuentaTransferencias(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Set<Transferencias> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Set<Transferencias> movimientos) {
        this.movimientos = movimientos;
    }

    public void addMovimientos(Movimiento movimientos) {
        this.movimientos.add(new Transferencias(movimientos));
    }

}
