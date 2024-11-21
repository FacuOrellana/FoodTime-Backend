package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.DietaDto;
import foodtime.app.sanatorio.dtos.DietaMenuDto;
import foodtime.app.sanatorio.models.*;
import foodtime.app.sanatorio.repositories.DietaMenuRepository;
import foodtime.app.sanatorio.repositories.DietaRepository;
import foodtime.app.sanatorio.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietaService extends BaseService<Dieta, DietaDto, Integer> {

    private final DietaRepository dietaRepository;
    private final PersonaRepository personaRepository;
    private final DietaMenuService dietaMenuService;
    private final DietaMenuRepository dietaMenuRepository;

    @Autowired
    public DietaService(DietaRepository dietaRepository, PersonaRepository personaRepository, DietaMenuService dietaMenuService, DietaMenuRepository dietaMenuRepository){
        this.dietaRepository = dietaRepository;
        this.personaRepository = personaRepository;
        this.dietaMenuService = dietaMenuService;
        this.dietaMenuRepository = dietaMenuRepository;
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
        Dieta dieta = toEntity(dto);
        Dieta savedDieta = dietaRepository.save(dieta);
        List<Integer> menuIds = dto.dietaMenuList().stream()
                .map(DietaMenuDto::menuId)
                .toList();
        dietaMenuService.createDietasMenu(savedDieta.getDietaMenus(),savedDieta,menuIds);
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
}
