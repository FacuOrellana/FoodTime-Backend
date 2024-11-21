package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Insumo;

import java.time.LocalDateTime;

public record InsumoDto(Integer id,
                        String nombre,
                        String unidad,
                        Integer precio,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                        boolean deleted) {
    public InsumoDto(Insumo insumo){
        this(insumo.getId(), insumo.getNombre(), insumo.getUnidad(), insumo.getPrecioUnidad(),insumo.getCreatedAt(),insumo.getModifiedAt(), insumo.isDeleted());
    }


}
