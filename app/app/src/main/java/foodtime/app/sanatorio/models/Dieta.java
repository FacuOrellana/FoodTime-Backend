package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.DietaDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Dieta")
@Table(name = "Dieta")
public class Dieta extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    private String detalles;

    @OneToMany(mappedBy = "dieta")
    Set<DietaMenu> dietaMenus = new HashSet<>();

    public Dieta() {}

    public Dieta(DietaDto entity){
        this.detalles = entity.detalles();
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Set<DietaMenu> getDietaMenus() {
        return dietaMenus;
    }

    public void setDietaMenus(Set<DietaMenu> dietaMenus) {
        this.dietaMenus = dietaMenus;
    }
}
