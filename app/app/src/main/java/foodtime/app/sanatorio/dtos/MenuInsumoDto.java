package foodtime.app.sanatorio.dtos;

import foodtime.app.sanatorio.models.MenuInsumo;

public record MenuInsumoDto(Integer id,
                            Integer menuId,
                            Integer insumoId,
                            Integer cantidad,
                            boolean deleted) {
    public MenuInsumoDto(MenuInsumo menuInsumo){
        this(menuInsumo.getId(), menuInsumo.getMenu().getId(), menuInsumo.getInsumo().getId(), menuInsumo.getCantidad(), menuInsumo.isDeleted());
    }
}