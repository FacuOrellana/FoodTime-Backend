package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.DietaDto;
import foodtime.app.sanatorio.dtos.DietaMenuDto;
import foodtime.app.sanatorio.models.*;
import foodtime.app.sanatorio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DietaService extends BaseService<Dieta, DietaDto, Integer> {

    private final DietaRepository dietaRepository;
    private final PersonaRepository personaRepository;
    private final DietaMenuService dietaMenuService;
    private final DietaMenuRepository dietaMenuRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoMenuRepository pedidoMenuRepository;

    @Autowired
    public DietaService(DietaRepository dietaRepository, PersonaRepository personaRepository, DietaMenuService dietaMenuService, DietaMenuRepository dietaMenuRepository, PedidoRepository pedidoRepository, PedidoMenuRepository pedidoMenuRepository){
        this.dietaRepository = dietaRepository;
        this.personaRepository = personaRepository;
        this.dietaMenuService = dietaMenuService;
        this.dietaMenuRepository = dietaMenuRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoMenuRepository = pedidoMenuRepository;
    }

    @Override
    protected BaseRepository<Dieta, Integer> getRepository() {
        return dietaRepository;
    }

    @Override
    protected DietaDto toDTO(Dieta entity) {
        List<DietaMenu> dietaMenuList = dietaMenuRepository.findAllByDietaIdAndDeletedIsFalse(entity.getId());
        return new DietaDto(entity,dietaMenuList);
    }

    @Override
    protected Dieta toEntity(DietaDto entity) {
        Dieta dieta = new Dieta(entity);
        Persona persona = personaRepository.findById(entity.personaId()).orElseThrow();
        dieta.setPersona(persona);
        dieta.setDietaMenus( entity.dietaMenuList() == null ? Collections.emptySet() :
                entity.dietaMenuList().stream().map(DietaMenu::new).collect(Collectors.toSet()));
        return dieta;
    }

    @Override
    @Transactional
    public DietaDto create(DietaDto dto) {
        if(!validateDieta(dto)){
            throw new EntityExistsException("La dieta ingresada no es valida, por favor verificar los menus seleccionados");
        }
        Dieta dieta = toEntity(dto);
        Dieta savedDieta = dietaRepository.save(dieta);
        List<Integer> menuIds = dto.dietaMenuList().stream()
                .map(DietaMenuDto::menuId)
                .toList();
        dietaMenuService.createDietasMenu(savedDieta.getDietaMenus(),savedDieta,menuIds);
        createPedidosFromDieta(savedDieta);
        return toDTO(savedDieta);
    }

    @Override
    @Transactional
    public DietaDto update(Integer dietaId, DietaDto dto) {
        Dieta entityToUpdate = toEntity(dto);
        entityToUpdate.setId(dietaId);
        Dieta savedEntity = getRepository().save(entityToUpdate);
        dietaMenuService.updateDietasMenu(dto.dietaMenuList(),savedEntity);
        return toDTO(savedEntity);
    }

    @Transactional
    public void createPedidosFromDieta(Dieta dieta) {
        for(DietaMenu dietaMenu : dieta.getDietaMenus()){
            Pedido pedido = new Pedido();
            pedido.setPersona(dieta.getPersona());
            pedido.setMetodoPago(MetodoPago.findByName("Dieta"));
            pedido.setUbicacion("Sanatorio");
            pedido.setEstadoPedido(EstadoPedido.findByName("PENDIENTE"));
            pedido.setCreatedAt(dietaMenu.getDia().atStartOfDay());
            pedido.setModifiedAt(dietaMenu.getDia().atStartOfDay());
            pedido.setMontoTotal(dietaMenu.getMenu().getPrecio());
            pedido.setDeleted(false);
            switch (dietaMenu.getTipoMenu().name()) {
                case "DESAYUNO":
                    pedido.setTiempoEntrega(dietaMenu.getDia().atTime(8, 0));
                    break;
                case "ALMUERZO":
                    pedido.setTiempoEntrega(dietaMenu.getDia().atTime(12, 30));
                    break;
                case "MERIENDA":
                    pedido.setTiempoEntrega(dietaMenu.getDia().atTime(18, 0));
                    break;
                case "CENA":
                    pedido.setTiempoEntrega(dietaMenu.getDia().atTime(21, 30));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de menú no reconocido: " + dietaMenu.getTipoMenu().name());
            }
            Pedido savedPedido = pedidoRepository.save(pedido);
            PedidoMenu pedidoMenu = new PedidoMenu();
            pedidoMenu.setMenu(dietaMenu.getMenu());
            pedidoMenu.setPedido(savedPedido);
            pedidoMenu.setCantidad(1);
            pedidoMenuRepository.save(pedidoMenu);


        }

    }

    public boolean validateDieta(DietaDto dietaDto) {
        // Usamos un mapa para almacenar los tipos de menú por día
        Map<LocalDate, Set<String>> menuPorDia = new HashMap<>();

        // Iteramos sobre la lista de DietaMenuDto
        for (DietaMenuDto dietaMenu : dietaDto.dietaMenuList()) {
            LocalDate dia = dietaMenu.dia();
            String tipoMenu = dietaMenu.tipoMenu();

            // Verificamos si ya existe un tipo de menú para ese día
            if (menuPorDia.containsKey(dia)) {
                Set<String> tiposMenuExistentes = menuPorDia.get(dia);

                // Si el tipo de menú ya existe en el set, entonces ya hay un menú para ese día
                if (tiposMenuExistentes.contains(tipoMenu)) {
                    return false;
                } else {
                    // Agregamos el nuevo tipo de menú al set
                    tiposMenuExistentes.add(tipoMenu);
                }
            } else {
                // Si no existe, creamos un nuevo set con el tipo de menú
                Set<String> tiposMenu = new HashSet<>();
                tiposMenu.add(tipoMenu);
                menuPorDia.put(dia, tiposMenu);
            }
        }

        return true;
    }


}
