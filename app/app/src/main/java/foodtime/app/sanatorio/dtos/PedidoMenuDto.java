package foodtime.app.sanatorio.dtos;

import foodtime.app.sanatorio.models.PedidoMenu;

public record PedidoMenuDto(Integer id,
                      Integer pedidoId,
                      Integer menuId,
                      Integer cantidad,
                      boolean deleted) {
    public PedidoMenuDto(PedidoMenu pedidoMenu){
        this(pedidoMenu.getId(), pedidoMenu.getPedido().getId(), pedidoMenu.getMenu().getId(), pedidoMenu.getCantidad(), pedidoMenu.isDeleted());
    }
}