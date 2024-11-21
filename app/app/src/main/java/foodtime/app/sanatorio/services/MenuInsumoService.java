package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.DietaMenuDto;
import foodtime.app.sanatorio.dtos.MenuInsumoDto;
import foodtime.app.sanatorio.models.*;
import foodtime.app.sanatorio.repositories.InsumoRepository;
import foodtime.app.sanatorio.repositories.MenuInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuInsumoService extends BaseService<MenuInsumo, MenuInsumoDto, Integer> {

    private final MenuInsumoRepository menuInsumoRepository;
    private final InsumoRepository insumoRepository;

    @Autowired
    public MenuInsumoService(MenuInsumoRepository menuInsumoRepository, InsumoRepository insumoRepository){
        this.menuInsumoRepository = menuInsumoRepository;
        this.insumoRepository = insumoRepository;
    }

    @Override
    protected BaseRepository<MenuInsumo, Integer> getRepository() {
        return menuInsumoRepository;
    }

    @Override
    protected MenuInsumoDto toDTO(MenuInsumo entity) {
        return new MenuInsumoDto(entity);
    }

    @Override
    protected MenuInsumo toEntity(MenuInsumoDto entity) {
        return new MenuInsumo(entity);
    }

    public Set<MenuInsumo> createMenuInsumos(Set<MenuInsumo> menusInsumos, Menu savedEntity, List<Integer> insumosIds) {
        List<Insumo> insumos = insumoRepository.findAllByDeletedIsFalseAndIdIn(insumosIds).stream().toList();
        List<MenuInsumo> menuInsumoList = new ArrayList<>(menusInsumos);
        for (int i = 0; i < menuInsumoList.size(); i++) {
            MenuInsumo menuInsumo = menuInsumoList.get(i);
            menuInsumo.setMenu(savedEntity);
            menuInsumo.setInsumo(insumos.get(i));
        }
        return new HashSet<>(menuInsumoRepository.saveAll(menusInsumos));
    }

    public void updateMenusInsumo(List<MenuInsumoDto> menuInsumoDtos, Menu menu) {
        // Lista actual en la base de datos
        List<MenuInsumo> currentMenusInsumoList = menuInsumoRepository.findAllByMenuIdAndDeletedIsFalse(menu.getId());

        // Lista de la solicitud PUT (convertimos Dto a entidad)
        List<MenuInsumo> menuInsumoRequest = menuInsumoDtos.stream().map(dto -> {
            MenuInsumo menuInsumo = new MenuInsumo(dto);
            menuInsumo.setId(dto.id());
            menuInsumo.setMenu(menu);  // Asignamos el menú asociado
            menuInsumo.setInsumo(insumoRepository.findById(dto.insumoId()).orElse(null));  // Obtenemos el insumo asociado
            return menuInsumo;
        }).toList();

        // Lista para almacenar los elementos a eliminar
        List<MenuInsumo> menusInsumoToDelete = new ArrayList<>();
        List<MenuInsumo> menusInsumoToSaveOrUpdate = new ArrayList<>();

        // Recorrer la lista actual para identificar elementos que deben eliminarse
        for (MenuInsumo currentMenuInsumo : currentMenusInsumoList) {
            // Si un item de la lista actual no está en la solicitud PUT, lo marcamos como eliminado
            boolean existsInRequest = menuInsumoRequest.stream().anyMatch(requestItem ->
                    requestItem.getId() != null && requestItem.getId().equals(currentMenuInsumo.getId())
            );

            if (!existsInRequest) {
                // Si no está en la solicitud, lo marcamos como eliminado
                currentMenuInsumo.setDeleted(true);
                menusInsumoToDelete.add(currentMenuInsumo);
            }
        }

        // Identificar los elementos nuevos o actualizados en la solicitud PUT
        for (MenuInsumo requestItem : menuInsumoRequest) {
            if (requestItem.getId() == null) {
                // Es un nuevo item, así que simplemente lo agregamos
                menusInsumoToSaveOrUpdate.add(requestItem);
            } else {
                // Si tiene id, comprobamos si ya está en la lista actual para actualizarlo
                MenuInsumo existingMenuInsumo = currentMenusInsumoList.stream()
                        .filter(currentMenuInsumo -> currentMenuInsumo.getId().equals(requestItem.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingMenuInsumo != null) {
                    // Actualizamos los campos relevantes del menú-insumo existente
                    existingMenuInsumo.setInsumo(requestItem.getInsumo());
                    existingMenuInsumo.setCantidad(requestItem.getCantidad());
                    menusInsumoToSaveOrUpdate.add(existingMenuInsumo);
                }
            }
        }

        // Asignamos el menú a los elementos que van a ser guardados o actualizados
        menusInsumoToSaveOrUpdate = menusInsumoToSaveOrUpdate.stream().peek(m -> m.setMenu(menu)).collect(Collectors.toList());

        // Guardamos los elementos actualizados o nuevos
        menuInsumoRepository.saveAll(menusInsumoToSaveOrUpdate);
        // Guardamos los elementos eliminados
        menuInsumoRepository.saveAll(menusInsumoToDelete);
    }



}
