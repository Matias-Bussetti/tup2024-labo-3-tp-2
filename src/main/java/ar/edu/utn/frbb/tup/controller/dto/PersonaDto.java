package ar.edu.utn.frbb.tup.controller.dto;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.model.Persona;
import ar.edu.utn.frbb.tup.model.tipos.TipoPersona;

public class PersonaDto {
    private String nombre;
    private String apellido;
    private long dni;
    private String fechaNacimiento;

    public String getNombre() {
        return nombre;
    }

    public PersonaDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public PersonaDto setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public long getDni() {
        return dni;
    }

    public PersonaDto setDni(long dni) {
        this.dni = dni;
        return this;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public PersonaDto setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }
}
