package foodtime.app.sanatorio.models;


import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.DietaMenuDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "DietaMenu")
@Table(name = "dieta_menu")
public class DietaMenu extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "dieta_id")
    private Dieta dieta;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private LocalDate dia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_menu")
    private TipoMenu tipoMenu;

    public DietaMenu() {}

    public DietaMenu(DietaMenuDto entity) {
        this.dia = entity.dia();
        this.tipoMenu = TipoMenu.findByName(entity.tipoMenu());
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public TipoMenu getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(TipoMenu tipoMenu) {
        this.tipoMenu = tipoMenu;
    }
}
