package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.MenuInsumo;

import java.util.List;

public interface MenuInsumoRepository extends BaseRepository<MenuInsumo, Integer> {

    List<MenuInsumo> findAllByMenuIdAndDeletedIsFalse(Integer menuId);
}
