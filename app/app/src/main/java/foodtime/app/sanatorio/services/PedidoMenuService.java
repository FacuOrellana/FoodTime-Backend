package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.PedidoMenuDto;
import foodtime.app.sanatorio.models.Menu;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.repositories.MenuRepository;
import foodtime.app.sanatorio.repositories.PedidoMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import foodtime.app.sanatorio.models.PedidoMenu;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoMenuService extends BaseService<PedidoMenu, PedidoMenuDto, Integer> {

    private final PedidoMenuRepository pedidoMenuRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public PedidoMenuService(PedidoMenuRepository pedidoMenuRepository, MenuRepository menuRepository){
        this.pedidoMenuRepository = pedidoMenuRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    protected BaseRepository<PedidoMenu, Integer> getRepository() {
        return pedidoMenuRepository;
    }

    @Override
    protected PedidoMenuDto toDTO(PedidoMenu entity) {
        return new PedidoMenuDto(entity);
    }

    @Override
    protected PedidoMenu toEntity(PedidoMenuDto entity) {
        return new PedidoMenu(entity);
    }

    public Set<PedidoMenu> createPedidosMenu(Set<PedidoMenu> pedidosMenu, Pedido savedEntity,List<Integer> menusIds) {
        List<Menu> menus = menuRepository.findAllByDeletedIsFalseAndIdIn(menusIds).stream().toList();
        List<PedidoMenu> pedidoMenuList = new ArrayList<>(pedidosMenu);
        for (int i = 0; i < pedidoMenuList.size(); i++) {
            PedidoMenu pedidoMenu = pedidoMenuList.get(i);
            pedidoMenu.setPedido(savedEntity);
            pedidoMenu.setMenu(menus.get(i));
        }
        return new HashSet<>(pedidoMenuRepository.saveAll(pedidosMenu));
    }

    public void updatePedidosMenu(List<PedidoMenuDto> pedidoMenuDtos, Pedido pedido) {
        // Lista actual en la base de datos
        List<PedidoMenu> currentPedidosMenuList = pedidoMenuRepository.findAllByPedidoIdAndDeletedIsFalse(pedido.getId());

        // Lista de la solicitud PUT (convertimos Dto a entidad)
        List<PedidoMenu> pedidoMenuRequest = pedidoMenuDtos.stream().map(dto -> {
            PedidoMenu pedidoMenu = new PedidoMenu(dto);
            pedidoMenu.setId(dto.id());
            pedidoMenu.setPedido(pedido);  // Asignamos el pedido asociado
            pedidoMenu.setMenu(menuRepository.findById(dto.menuId()).orElse(null));  // Obtenemos el menú asociado
            return pedidoMenu;
        }).toList();

        // Lista para almacenar los elementos a eliminar
        List<PedidoMenu> pedidosMenuToDelete = new ArrayList<>();
        List<PedidoMenu> pedidosMenuToSaveOrUpdate = new ArrayList<>();

        // Recorrer la lista actual para identificar elementos que deben eliminarse
        for (PedidoMenu currentPedidoMenu : currentPedidosMenuList) {
            // Si un item de la lista actual no está en la solicitud PUT, lo marcamos como eliminado
            boolean existsInRequest = pedidoMenuRequest.stream().anyMatch(requestItem ->
                    requestItem.getId() != null && requestItem.getId().equals(currentPedidoMenu.getId())
            );

            if (!existsInRequest) {
                // Si no está en la solicitud, lo marcamos como eliminado
                currentPedidoMenu.setDeleted(true);
                pedidosMenuToDelete.add(currentPedidoMenu);
            }
        }

        // Identificar los elementos nuevos o actualizados en la solicitud PUT
        for (PedidoMenu requestItem : pedidoMenuRequest) {
            if (requestItem.getId() == null) {
                // Es un nuevo item, así que simplemente lo agregamos
                pedidosMenuToSaveOrUpdate.add(requestItem);
            } else {
                // Si tiene id, comprobamos si ya está en la lista actual para actualizarlo
                PedidoMenu existingPedidoMenu = currentPedidosMenuList.stream()
                        .filter(currentPedidoMenu -> currentPedidoMenu.getId().equals(requestItem.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingPedidoMenu != null) {
                    // Actualizamos los campos relevantes del pedido existente
                    existingPedidoMenu.setMenu(requestItem.getMenu());
                    existingPedidoMenu.setCantidad(requestItem.getCantidad());
                    pedidosMenuToSaveOrUpdate.add(existingPedidoMenu);
                }
            }
        }
        pedidosMenuToSaveOrUpdate = pedidosMenuToSaveOrUpdate.stream().peek(p -> p.setPedido(pedido)).collect(Collectors.toList());

        pedidoMenuRepository.saveAll(pedidosMenuToSaveOrUpdate);
        pedidoMenuRepository.saveAll(pedidosMenuToDelete);
    }
}
