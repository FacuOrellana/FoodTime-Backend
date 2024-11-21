package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Menu;
import foodtime.app.sanatorio.models.MenuInsumo;

import java.time.LocalDateTime;
import java.util.List;

public record MenuDto(Integer id,
                         String titulo,
                         String descripcion,
                         Integer precio,
                         Boolean disponibilidad,
                         String tipoMenu,
                         List<MenuInsumoDto> menuInsumoList,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                         boolean deleted) {
    public MenuDto(Menu menu, List<MenuInsumo> menuInsumoList){
        this(menu.getId(), menu.getTitulo(), menu.getDescripcion(), menu.getPrecio(), menu.isDisponibilidad(),
                menu.getTipoMenu().name(),menuInsumoList.stream().map(MenuInsumoDto::new).toList(),
                menu.getCreatedAt(),menu.getModifiedAt(), menu.isDeleted());
    }
}