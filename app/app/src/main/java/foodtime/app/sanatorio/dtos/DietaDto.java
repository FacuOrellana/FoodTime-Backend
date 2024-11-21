package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Dieta;
import foodtime.app.sanatorio.models.DietaMenu;

import java.time.LocalDateTime;
import java.util.List;

public record DietaDto(
                        Integer id,
                        Integer personaId,
                        String detalles,
                        List<DietaMenuDto> dietaMenuList,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                        boolean deleted) {
    public DietaDto(Dieta dieta,List<DietaMenu> dietaMenuList) {
        this(dieta.getId(),dieta.getPersona().getId(),dieta.getDetalles(),
                dietaMenuList.stream().map(DietaMenuDto::new).toList(),
                dieta.getCreatedAt(),dieta.getModifiedAt(),dieta.isDeleted());
    }

}
