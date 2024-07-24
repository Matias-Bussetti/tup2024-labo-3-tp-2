package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;

public class Cliente extends Persona {

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;

    @JsonManagedReference // Para evitar la serialización circular y bucles infinitos en la conversión de
                          // objetos a JSON, puedes usar varias estrategias en Spring Boot. Aquí te dejo
                          // algunas opciones:
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

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    // Added
    public void setTipoPersona(String tipoPersona) {
        if ("F".equals(tipoPersona)) {
            this.tipoPersona = TipoPersona.PERSONA_FISICA;
        } else if ("J".equals(tipoPersona)) {
            this.tipoPersona = TipoPersona.PERSONA_JURIDICA;
        }
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(this);
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
