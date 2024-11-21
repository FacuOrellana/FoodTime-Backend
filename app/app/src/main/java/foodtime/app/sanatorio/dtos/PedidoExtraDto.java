package foodtime.app.sanatorio.dtos;

import foodtime.app.sanatorio.models.PedidoExtra;

public record PedidoExtraDto(Integer id,
                             Integer pedidoId,
                             String nombre,
                             String apellido,
                             String dni,
                             String email,
                             String numeroTelefono,
                             boolean deleted) {
    public PedidoExtraDto(PedidoExtra pedidoExtra){
        this(pedidoExtra.getId(), pedidoExtra.getPedido().getId(), pedidoExtra.getNombre(), pedidoExtra.getApellido(),
                pedidoExtra.getDni(), pedidoExtra.getEmail(), pedidoExtra.getNumeroTelefono(), pedidoExtra.isDeleted());
    }
}
