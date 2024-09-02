package ar.edu.utn.frbb.tup.controller.dto;

public class TransferenciaDto {
    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String moneda;

    @Override
    public String toString() {
        return "TransferenciaDto [cuentaOrigen=" + cuentaOrigen + ", cuentaDestino=" + cuentaDestino + ", monto="
                + monto + ", moneda=" + moneda + "]";
    }

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public TransferenciaDto setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
        return this;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public TransferenciaDto setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
        return this;
    }

    public double getMonto() {
        return monto;
    }

    public TransferenciaDto setMonto(double monto) {
        this.monto = monto;
        return this;
    }

    public String getMoneda() {
        return moneda;
    }

    public TransferenciaDto setMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

}
