package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.tipos.TipoCuenta;
import ar.edu.utn.frbb.tup.model.tipos.TipoMoneda;
import ar.edu.utn.frbb.tup.model.tipos.TipoPersona;

public class Cliente extends Persona {

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {
        super();
    }

    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getDni(), clienteDto.getApellido(), clienteDto.getNombre(), clienteDto.getFechaNacimiento());
        fechaAlta = LocalDate.now();
        banco = clienteDto.getBanco();
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public Cliente setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
        return this;
    }

    public Cliente setTipoPersona(String tipoPersona) {
        if ("F".equals(tipoPersona)) {
            this.tipoPersona = TipoPersona.PERSONA_FISICA;
        } else if ("J".equals(tipoPersona)) {
            this.tipoPersona = TipoPersona.PERSONA_JURIDICA;
        }
        return this;
    }

    public String getBanco() {
        return banco;
    }

    public Cliente setBanco(String banco) {
        this.banco = banco;
        return this;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Cliente setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public Cliente addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(this);
        return this;
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        for (Cuenta cuenta : cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "tipoPersona=" + tipoPersona +
                ", banco='" + banco + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", cuentas=" + cuentas +
                '}';
    }

}
