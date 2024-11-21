package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.MenuDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Menu")
@Table(name = "Menu")
public class Menu extends BaseEntity {

    private String titulo;
    private String descripcion;
    private Integer precio;
    private boolean disponibilidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_menu")
    private TipoMenu tipoMenu;

    @OneToMany(mappedBy = "insumo")
    Set<MenuInsumo> menuInsumos = new HashSet<>();

    public Menu() {}

    public Menu(MenuDto entity) {
        this.titulo = entity.titulo();
        this.descripcion = entity.descripcion();
        this.precio = entity.precio();
        this.disponibilidad = entity.disponibilidad();
        this.tipoMenu = TipoMenu.findByName(entity.tipoMenu());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponible) {
        this.disponibilidad = disponible;
    }

    public TipoMenu getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(TipoMenu tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public Set<MenuInsumo> getMenuInsumos() {
        return menuInsumos;
    }

    public void setMenuInsumos(Set<MenuInsumo> menuInsumos) {
        this.menuInsumos = menuInsumos;
    }
}

