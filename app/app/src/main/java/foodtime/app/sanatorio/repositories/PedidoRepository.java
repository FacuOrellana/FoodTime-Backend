package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.repositories.projections.EmpleadosPedidos;
import foodtime.app.sanatorio.repositories.projections.PedidosByPersona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends BaseRepository<Pedido, Integer> {

    @Query(value = "SELECT p.id AS pedidoId, ps.dni, ps.nombre, ps.apellido, p.metodo_pago as metodoPago, CAST(CAST(p.created_at AS DATE) AS DATE) AS fechaPedido, SUM(pm.cantidad) AS cantidadMenus, p.estado_pedido AS estadoPedido, p.monto_total AS montoTotal "
            + " FROM Pedido p JOIN Persona ps ON p.persona_id = ps.id JOIN pedido_menu pm ON pm.pedido_id = p.id "
            + " WHERE p.deleted = 0 AND p.persona_id = :personaId AND pm.deleted = 0 AND ps.deleted = 0 "
            + " GROUP BY p.id, ps.dni, p.metodo_pago, p.monto_total, ps.nombre, ps.apellido, p.created_at, p.estado_pedido",
            nativeQuery = true)
    List<PedidosByPersona> getPedidosByPersona(@Param("personaId") Integer personaId);

    @Query(value = "SELECT p.id AS pedidoId, pe.dni, pe.nombre, pe.apellido, p.metodo_pago as metodoPago, CAST(CAST(p.created_at AS DATE) AS DATE) AS fechaPedido, SUM(pm.cantidad) AS cantidadMenus, p.estado_pedido AS estadoPedido, p.monto_total AS montoTotal "
            + " FROM Pedido p JOIN pedido_extra pe ON pe.pedido_id = p.id JOIN pedido_menu pm ON pm.pedido_id = p.id "
            + " WHERE p.deleted = 0 AND pe.dni = :dni AND pm.deleted = 0 AND pe.deleted = 0 "
            + " GROUP BY p.id, pe.dni, p.metodo_pago, p.monto_total, pe.nombre, pe.apellido, p.created_at, p.estado_pedido",
            nativeQuery = true)
    List<PedidosByPersona> getPedidosByDni(@Param("dni") String dni);

    @Query(value = "SELECT p.id AS pedidoId, ps.dni, ps.nombre, ps.apellido, p.metodo_pago as metodoPago, CAST(CAST(p.created_at AS DATE) AS DATE) AS fechaPedido, SUM(pm.cantidad) AS cantidadMenus, p.estado_pedido AS estadoPedido, p.monto_total AS montoTotal "
            + " FROM Pedido p JOIN Persona ps ON p.persona_id = ps.id JOIN pedido_menu pm ON pm.pedido_id = p.id "
            + " WHERE p.deleted = 0 AND pm.deleted = 0 AND ps.deleted = 0 AND p.metodo_pago = 'CUENTA' " +
            "GROUP BY p.id, ps.dni, p.metodo_pago, p.monto_total, ps.nombre, ps.apellido, p.created_at, p.estado_pedido",
            nativeQuery = true)
    List<EmpleadosPedidos> getEmpleadosPedidos();

    @Query(value ="SELECT persona_id FROM Pedido WHERE id = :id",nativeQuery = true)
    Integer getPersonaIdByPedidoId(@Param("id") Integer id);



}