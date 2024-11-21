package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.DietaMenu;

import java.time.LocalDate;

public record DietaMenuDto(Integer id,
                           Integer dietaId,
                           Integer menuId,
                           @JsonFormat(pattern = "yyyy-MM-dd")LocalDate dia,
                           String tipoMenu,
                           boolean deleted) {
    public DietaMenuDto(DietaMenu dietaMenu){
        this(dietaMenu.getId(), dietaMenu.getDieta().getId(), dietaMenu.getMenu().getId(), dietaMenu.getDia(),dietaMenu.getTipoMenu().name(), dietaMenu.isDeleted());
    }
}
