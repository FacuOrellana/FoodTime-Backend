package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.DietaDto;
import foodtime.app.sanatorio.dtos.DietaMenuDto;
import foodtime.app.sanatorio.dtos.PedidoDto;
import foodtime.app.sanatorio.models.Menu;
import foodtime.app.sanatorio.models.Dieta;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.repositories.MenuRepository;
import foodtime.app.sanatorio.repositories.DietaMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import foodtime.app.sanatorio.models.DietaMenu;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DietaMenuService extends BaseService<DietaMenu, DietaMenuDto, Integer> {

    private final DietaMenuRepository dietaMenuRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public DietaMenuService(DietaMenuRepository dietaMenuRepository, MenuRepository menuRepository){
        this.dietaMenuRepository = dietaMenuRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    protected BaseRepository<DietaMenu, Integer> getRepository() {
        return dietaMenuRepository;
    }

    @Override
    protected DietaMenuDto toDTO(DietaMenu entity) {
        return new DietaMenuDto(entity);
    }

    @Override
    protected DietaMenu toEntity(DietaMenuDto entity) {
        return new DietaMenu(entity);
    }

    public Set<DietaMenu> createDietasMenu(Set<DietaMenu> dietasMenu, Dieta savedEntity, List<Integer> menusIds) {
        List<Menu> menus = menuRepository.findAllByDeletedIsFalseAndIdIn(menusIds).stream().toList();
        List<DietaMenu> dietaMenuList = new ArrayList<>(dietasMenu);
        for (int i = 0; i < dietaMenuList.size(); i++) {
            DietaMenu dietaMenu = dietaMenuList.get(i);
            dietaMenu.setDieta(savedEntity);
            dietaMenu.setMenu(menus.get(i));
        }
        return new HashSet<>(dietaMenuRepository.saveAll(dietasMenu));
    }

    public void updateDietasMenu(List<DietaMenuDto> dietaMenuDtos, Dieta dieta) {
        // Lista actual en la base de datos
        List<DietaMenu> currentDietasMenuList = dietaMenuRepository.findAllByDietaIdAndDeletedIsFalse(dieta.getId());

        // Lista de la solicitud PUT (convertimos Dto a entidad)
        List<DietaMenu> dietaMenuRequest = dietaMenuDtos.stream().map(dto -> {
            DietaMenu dietaMenu = new DietaMenu(dto);
            dietaMenu.setId(dto.id());
            dietaMenu.setDieta(dieta);  // Asignamos la dieta asociada
            dietaMenu.setMenu(menuRepository.findById(dto.menuId()).orElse(null));  // Obtenemos el menú asociado
            return dietaMenu;
        }).toList();

        // Lista para almacenar los elementos a eliminar
        List<DietaMenu> dietasMenuToDelete = new ArrayList<>();
        List<DietaMenu> dietasMenuToSaveOrUpdate = new ArrayList<>();

        // Recorrer la lista actual para identificar elementos que deben eliminarse
        for (DietaMenu currentDietaMenu : currentDietasMenuList) {
            // Si un item de la lista actual no está en la solicitud PUT, lo marcamos como eliminado
            boolean existsInRequest = dietaMenuRequest.stream().anyMatch(requestItem ->
                    requestItem.getId() != null && requestItem.getId().equals(currentDietaMenu.getId())
            );

            if (!existsInRequest) {
                // Si no está en la solicitud, lo marcamos como eliminado
                currentDietaMenu.setDeleted(true);
                dietasMenuToDelete.add(currentDietaMenu);
            }
        }

        // Identificar los elementos nuevos o actualizados en la solicitud PUT
        for (DietaMenu requestItem : dietaMenuRequest) {
            if (requestItem.getId() == null) {
                // Es un nuevo item, así que simplemente lo agregamos
                dietasMenuToSaveOrUpdate.add(requestItem);
            } else {
                // Si tiene id, comprobamos si ya está en la lista actual para actualizarlo
                DietaMenu existingDietaMenu = currentDietasMenuList.stream()
                        .filter(currentDietaMenu -> currentDietaMenu.getId().equals(requestItem.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingDietaMenu != null) {
                    // Actualizamos los campos relevantes de la dieta existente
                    existingDietaMenu.setMenu(requestItem.getMenu());
                    existingDietaMenu.setDia(requestItem.getDia());
                    existingDietaMenu.setTipoMenu(requestItem.getTipoMenu());
                    dietasMenuToSaveOrUpdate.add(existingDietaMenu);
                }
            }
        }

        dietasMenuToSaveOrUpdate = dietasMenuToSaveOrUpdate.stream().peek(d -> d.setDieta(dieta)).collect(Collectors.toList());

        // Guardamos los elementos actualizados o nuevos
        dietaMenuRepository.saveAll(dietasMenuToSaveOrUpdate);
        // Guardamos los elementos eliminados
        dietaMenuRepository.saveAll(dietasMenuToDelete);
    }

}
