package foodtime.app.common;


import foodtime.app.common.exceptions.InvalidRequestParametersException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseService<Entity extends BaseEntity, DTO, Key> implements CrudService<DTO, Key> {

    private static final Logger LOGGER = LogManager.getLogger();

    protected BaseService() {
    }

    @Override
    @Transactional
    public Optional<DTO> findOne(Key id) {
        return getRepository().findById(id)
                .map(this::toDTO);
    }

    @Override
    @Transactional
    public List<DTO> findAll() {
        try {
            List<DTO> list = getRepository().findAllByDeletedIsFalse().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(list).getBody();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new InvalidRequestParametersException("Bad params error", e);
        }
    }

    @Override
    @Transactional
    public DTO delete(Key id) {
        Entity entity = getRepository().findById(id).orElseThrow();
        entity.setDeleted(true);
        return toDTO(entity);
    }

    @Override
    @Transactional
    public DTO create(DTO entity) {
        Entity entityToCreate = toEntity(entity);
        Entity savedEntity = getRepository().save(entityToCreate);
        return toDTO(savedEntity);
    }

    @Override
    @Transactional
    public DTO update(Key id, DTO entity) {
        Entity entityToUpdate = toEntity(entity);
        entityToUpdate.setId((Integer) id);
        Entity savedEntity = getRepository().save(entityToUpdate);
        return toDTO(savedEntity);
    }

    protected abstract BaseRepository<Entity, Key> getRepository();

    protected abstract DTO toDTO(Entity entity);

    protected abstract Entity toEntity(DTO entity);

}
