package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.PersonaDto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Persona")
@Table(name = "persona")
public class Persona extends BaseEntity {

    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String numeroTelefono;
    private String direccion;
    private LocalDate fechaNacimiento;

    public Persona(){
    }

    public Persona(PersonaDto entity) {
        this.dni = entity.dni();
        this.nombre = entity.nombre();
        this.apellido = entity.apellido();
        this.email = entity.email();
        this.numeroTelefono = entity.numeroTelefono();
        this.direccion = entity.direccion();
        this.fechaNacimiento = entity.fechaNacimiento();
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
