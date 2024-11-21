package foodtime.app.sanatorio.models;


import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.PedidoMenuDto;

import javax.persistence.*;

@Entity(name = "PedidoMenu")
@Table(name = "pedido_menu")
public class PedidoMenu extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int cantidad;

    public PedidoMenu() {}

    public PedidoMenu(PedidoMenuDto entity) {
        this.cantidad = entity.cantidad();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
