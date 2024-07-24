package ar.edu.utn.frbb.tup.model;

import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;

public class Cuenta {
    private long numeroCuenta;
    LocalDateTime fechaCreacion;
    double balance;
    TipoCuenta tipoCuenta;

    @JsonBackReference // Estas anotaciones de Jackson pueden ayudarte a romper la referencia circular.
    Cliente titular;

    TipoMoneda moneda;

    public Cuenta(CuentaDto cuentaDto) {
        this.numeroCuenta = new Random().nextLong();
        // this.balance = 0;
        this.balance = 1000;
        this.fechaCreacion = LocalDateTime.now();

        this.tipoCuenta = cuentaDto.gTipoCuenta();
        this.moneda = cuentaDto.gTipoMoneda();
    }

    public Cuenta() {
        this.numeroCuenta = new Random().nextLong();
        this.balance = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Cuenta setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public Cuenta setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
        return this;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cuenta setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public Cuenta setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public void debitarDeCuenta(double cantidadADebitar) throws NoAlcanzaException, CantidadNegativaException {
        if (cantidadADebitar < 0) {
            throw new CantidadNegativaException();
        }

        if (balance < cantidadADebitar) {
            throw new NoAlcanzaException();
        }
        this.balance = this.balance - cantidadADebitar;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void forzaDebitoDeCuenta(double i) {
        this.balance = this.balance - i;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

}
