package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.Period;

public class Persona {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;

    public Persona() {
    }

    public Persona(long dni, String apellido, String nombre, String fechaNacimiento) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
    }

    public String getNombre() {
        return nombre;
    }

    public Persona setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Persona setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public long getDni() {
        return dni;
    }

    public Persona setDni(long dni) {
        this.dni = dni;
        return this;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Persona setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public int getEdad() {
        LocalDate currentDate = LocalDate.now();
        Period agePeriod = Period.between(fechaNacimiento, currentDate);
        return agePeriod.getYears();
    }
}
