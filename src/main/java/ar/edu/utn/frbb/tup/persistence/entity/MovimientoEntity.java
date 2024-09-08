package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.tipos.TipoMovimiento;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

import java.time.LocalDateTime;

public class MovimientoEntity extends BaseEntity {

    private long id;
    private LocalDateTime fechaCreacion;
    private Long cuenta;
    private double monto;
    private String descripcionBreve;
    private String tipo;

    public MovimientoEntity() {
        super(-1);
    }

    public MovimientoEntity(Movimiento movimiento) {
        super(movimiento.getId());
        this.id = movimiento.getId();
        this.cuenta = movimiento.getCuenta().getNumeroCuenta();
        this.monto = movimiento.getMonto();
        this.descripcionBreve = movimiento.getDescripcionBreve();
        this.tipo = movimiento.getTipo().toString();
        this.fechaCreacion = movimiento.getFechaCreacion();
    }

    public Movimiento toMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setMonto(this.monto);
        movimiento.setId(this.id);
        movimiento.setTipo(TipoMovimiento.valueOf(this.tipo.toUpperCase()));
        movimiento.setDescripcionBreve(this.descripcionBreve);
        movimiento.setFechaCreacion(this.fechaCreacion);

        if (this.cuenta != null) {
            CuentaDao clienteDao = new CuentaDao();
            movimiento.setCuenta(clienteDao.find(this.cuenta));
        }

        return movimiento;
    }

    public Long getId() {
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

    public long getCuenta() {
        return cuenta;
    }

    public void setCuenta(long cuenta) {
        this.cuenta = cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
