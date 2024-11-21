package foodtime.app.sanatorio.models;


import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.MenuInsumoDto;
import foodtime.app.sanatorio.dtos.PedidoMenuDto;

import javax.persistence.*;

@Entity(name = "MenuInsumo")
@Table(name = "menu_insumo")
public class MenuInsumo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int cantidad;

    public MenuInsumo() {}

    public MenuInsumo(MenuInsumoDto entity) {
        this.cantidad = entity.cantidad();
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
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
