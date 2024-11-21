package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.models.PedidoMenu;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(Integer id,
                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime tiempoEntrega,
                       String metodoPago,
                       Integer total,
                       Integer personaId,
                       List<PedidoMenuDto> pedidoMenuList,
                       String estadoPedido,
                       String ubicacion,
                       PedidoExtraDto extra,
                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                       boolean deleted) {
    public PedidoDto(Pedido pedido, List<PedidoMenu> pedidoMenuList, PedidoExtraDto extra) {
        this(pedido.getId(),pedido.getTiempoEntrega(), pedido.getMetodoPago().name(), pedido.getMontoTotal(), pedido.getPersona() != null? pedido.getPersona().getId() : null,
                pedidoMenuList.stream().map(PedidoMenuDto::new).toList(),pedido.getEstadoPedido().name(), pedido.getUbicacion(), extra,
                pedido.getCreatedAt(),pedido.getModifiedAt(), pedido.isDeleted());
    }

}
