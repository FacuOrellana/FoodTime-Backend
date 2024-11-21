package foodtime.app.sanatorio.repositories.projections;

import java.time.LocalDate;

public interface EmpleadosPedidos {
    Integer getPedidoId();

    String getDni();

    String getNombre();

    String getApellido();

    String getMetodoPago();

    LocalDate getFechaPedido();

    Integer getCantidadMenus();

    String getEstadoPedido();

    Integer getMontoTotal();
}
