package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.PedidoMenu;

import java.util.List;

public interface PedidoMenuRepository extends BaseRepository<PedidoMenu, Integer> {

    List<PedidoMenu> findAllByPedidoIdAndDeletedIsFalse(Integer pedidoId);
}
