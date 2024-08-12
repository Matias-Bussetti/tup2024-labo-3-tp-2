package ar.edu.utn.frbb.tup.controller.dto;

import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;

public class CuentaDto {
    private String tipoCuenta;
    private String moneda;

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public TipoCuenta parseTipoCuenta() {
        return TipoCuenta.valueOf(tipoCuenta);
    }

    public TipoMoneda parseTipoMoneda() {
        return TipoMoneda.valueOf(moneda);
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
