package ar.edu.utn.frbb.tup.controller.dto;

import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;

public class CuentaDto {
    private String tipoCuenta;
    private String moneda;

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public CuentaDto setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public String getMoneda() {
        return moneda;
    }

    public TipoCuenta parseTipoCuenta() {
        return TipoCuenta.valueOf(tipoCuenta.toUpperCase());
    }

    public TipoMoneda parseTipoMoneda() {
        return TipoMoneda.valueOf(moneda.toUpperCase());
    }

    public CuentaDto setMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

}
