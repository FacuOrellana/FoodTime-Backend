package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.PedidoDto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Pedido")
@Table(name = "Pedido")
public class Pedido extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;
    private LocalDateTime tiempoEntrega;
    private Integer montoTotal;
    private String ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @OneToMany(mappedBy = "pedido")
    Set<PedidoMenu> pedidoMenus = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido")
    private EstadoPedido estadoPedido;


    public Pedido (){}

    public Pedido (PedidoDto pedidoDto){
        this.montoTotal = pedidoDto.total();
        this.tiempoEntrega = pedidoDto.tiempoEntrega();
        this.metodoPago = MetodoPago.findByName(pedidoDto.metodoPago());
        this.estadoPedido = EstadoPedido.findByName(pedidoDto.estadoPedido());
        this.ubicacion = pedidoDto.ubicacion();

    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(LocalDateTime tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Set<PedidoMenu> getPedidoMenus() {
        return pedidoMenus;
    }

    public void setPedidoMenus(Set<PedidoMenu> pedidoMenus) {
        this.pedidoMenus = pedidoMenus;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
