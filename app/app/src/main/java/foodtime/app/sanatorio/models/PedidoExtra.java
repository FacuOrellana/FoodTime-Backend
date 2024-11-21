package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.PedidoExtraDto;

import javax.persistence.*;

@Entity(name = "PedidoExtra")
@Table(name = "pedido_extra")
public class PedidoExtra extends BaseEntity {

    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String numeroTelefono;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public PedidoExtra() {}

    public PedidoExtra(PedidoExtraDto dto) {
         this.dni = dto.dni();
         this.nombre = dto.nombre();
         this.apellido = dto.apellido();
         this.email = dto.email();
         this.numeroTelefono = dto.numeroTelefono();
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
