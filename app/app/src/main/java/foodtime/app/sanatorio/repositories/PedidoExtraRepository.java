package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.PedidoExtra;

public interface PedidoExtraRepository extends BaseRepository<PedidoExtra,Integer> {

    PedidoExtra findByPedidoId(Integer id);

}
