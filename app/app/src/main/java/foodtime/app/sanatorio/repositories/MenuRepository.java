package foodtime.app.sanatorio.repositories;

import foodtime.app.common.BaseRepository;
import foodtime.app.sanatorio.models.Menu;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface MenuRepository extends BaseRepository<Menu, Integer> {

    Set<Menu> findAllByDeletedIsFalseAndIdIn(List<Integer> ids);

    Optional<Menu> findByTitulo(String titulo);
}