package ar.edu.utn.frbb.tup.model;

public class Transferencias {
    private String fecha;
    private String tipo;
    private String descripcionBreve;
    private double monto;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Transferencias(String fecha, String tipo, String descripcionBreve, double monto) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcionBreve = descripcionBreve;
        this.monto = monto;
    }

    public Transferencias(Movimiento movimiento) {
        this.fecha = movimiento.getFechaCreacion().toString();
        this.tipo = "Transfencia";
        this.descripcionBreve = "Movimiento a " + movimiento.getCuentaDestino();
        this.monto = movimiento.getBalance();
    }

}
