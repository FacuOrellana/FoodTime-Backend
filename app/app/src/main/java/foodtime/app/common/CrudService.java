package foodtime.app.common;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CrudService<DTO, Key> {

    Optional<DTO> findOne(Key id);

    List<DTO> findAll();

    DTO delete(Key id);

    DTO create(DTO entity);

    DTO update(Key id, DTO entity);

}
