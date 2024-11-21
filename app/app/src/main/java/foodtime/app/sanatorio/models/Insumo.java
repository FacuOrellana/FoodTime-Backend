package foodtime.app.sanatorio.models;

import foodtime.app.common.BaseEntity;
import foodtime.app.sanatorio.dtos.InsumoDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Insumo")
@Table(name = "Insumo")
public class Insumo extends BaseEntity {

    @Column
    private String nombre;

    @Column
    private String unidad;

    @Column
    private Integer precioUnidad;

    public Insumo() {}

    public Insumo(InsumoDto insumoDto) {
        this.nombre = insumoDto.nombre();
        this.unidad = insumoDto.unidad();
        this.precioUnidad = insumoDto.precio();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Integer precioUnidad) {
        this.precioUnidad = precioUnidad;
    }
}
