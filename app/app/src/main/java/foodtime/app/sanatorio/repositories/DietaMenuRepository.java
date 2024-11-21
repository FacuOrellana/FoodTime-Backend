package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.DietaMenu;

import java.util.List;

public interface DietaMenuRepository extends BaseRepository<DietaMenu, Integer> {

    List<DietaMenu> findAllByDietaIdAndDeletedIsFalse(Integer dietaId);
}
