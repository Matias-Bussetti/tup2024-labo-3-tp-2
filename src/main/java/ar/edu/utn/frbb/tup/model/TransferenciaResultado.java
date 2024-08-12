package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.model.tipos.TipoEstadoDeTransferencia;

public class TransferenciaResultado {
    private TipoEstadoDeTransferencia estado;
    private String mensaje;

    public TransferenciaResultado() {
        this.estado = TipoEstadoDeTransferencia.FALLIDA;
        this.mensaje = "";
    }

    public TransferenciaResultado(TipoEstadoDeTransferencia estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public TipoEstadoDeTransferencia getEstado() {
        return estado;
    }

    public TransferenciaResultado setEstado(TipoEstadoDeTransferencia estado) {
        this.estado = estado;
        return this;
    }

    public String getMensaje() {
        return mensaje;
    }

    public TransferenciaResultado setMensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    @Override
    public String toString() {
        return "TransferenciaResultado [estado=" + estado + ", mensaje=" + mensaje + "]";
    }

}
