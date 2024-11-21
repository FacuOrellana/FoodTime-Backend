package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.MenuDto;
import foodtime.app.sanatorio.dtos.MenuInsumoDto;
import foodtime.app.sanatorio.models.MenuInsumo;
import foodtime.app.sanatorio.repositories.MenuInsumoRepository;
import foodtime.app.sanatorio.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import foodtime.app.sanatorio.models.Menu;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService extends BaseService<Menu, MenuDto, Integer> {

    private final MenuRepository menuRepository;
    private final MenuInsumoService menuInsumoService;
    private final MenuInsumoRepository menuInsumoRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, MenuInsumoService menuInsumoService, MenuInsumoRepository menuInsumoRepository){
        this.menuRepository = menuRepository;
        this.menuInsumoService = menuInsumoService;
        this.menuInsumoRepository = menuInsumoRepository;
    }

    @Override
    protected BaseRepository<Menu, Integer> getRepository() {
        return menuRepository;
    }

    @Override
    protected MenuDto toDTO(Menu entity) {
        List<MenuInsumo> menuInsumosList = menuInsumoRepository.findAllByMenuIdAndDeletedIsFalse(entity.getId());
        return new MenuDto(entity,menuInsumosList);
    }

    @Override
    protected Menu toEntity(MenuDto entity) {
        Menu menu = new Menu(entity);
        menu.setMenuInsumos(entity.menuInsumoList() == null ? Collections.emptySet() :
                entity.menuInsumoList().stream().map(MenuInsumo::new).collect(Collectors.toSet()));
        return menu;
    }

    @Override
    @Transactional
    public MenuDto create(MenuDto dto) {
        Menu menu = toEntity(dto);
        Menu savedMenu = menuRepository.save(menu);
        List<Integer> insumosIds = dto.menuInsumoList().stream()
                .map(MenuInsumoDto::insumoId)
                .toList();
        menuInsumoService.createMenuInsumos(savedMenu.getMenuInsumos(),savedMenu,insumosIds);
        return toDTO(savedMenu);
    }

    @Override
    @Transactional
    public MenuDto update(Integer menuId, MenuDto dto) {
        Menu entityToUpdate = toEntity(dto);
        entityToUpdate.setId(menuId);
        Menu savedEntity = getRepository().save(entityToUpdate);
        menuInsumoService.updateMenusInsumo(dto.menuInsumoList(),savedEntity);
        return toDTO(savedEntity);
    }
}
