package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.Insumo;
import foodtime.app.sanatorio.repositories.projections.InsumosPorFecha;
import foodtime.app.sanatorio.repositories.projections.PedidosByPersona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface InsumoRepository extends BaseRepository<Insumo, Integer> {

    Set<Insumo> findAllByDeletedIsFalseAndIdIn(List<Integer> insumosIds);

    @Query(value = """

            SELECT\s
            i.nombre AS insumo,
            SUM(mi.cantidad * pm.cantidad) AS cantidadUtilizada,
            i.unidad AS unidad
        FROM\s
            pedido_Menu pm
        INNER JOIN\s
            Menu m ON pm.menu_id = m.id
        INNER JOIN\s
            menu_insumo mi ON m.id = mi.menu_id
        INNER JOIN\s
            Insumo i ON mi.insumo_id = i.id
        INNER JOIN\s
            pedido p ON p.id = pm.pedido_id
        WHERE\s
            pm.deleted = 0 AND
            m.deleted = 0 AND
            mi.deleted = 0 AND
            i.deleted = 0 AND
            p.deleted = 0 AND
            p.created_at >= CAST(:fechaInicio AS DATE) AND
            p.created_at <= CAST(:fechaFin AS DATE)
        GROUP BY\s
            i.nombre, i.unidad;
        
        """, nativeQuery = true)
    List<InsumosPorFecha> getInsumosPorFecha(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);


}