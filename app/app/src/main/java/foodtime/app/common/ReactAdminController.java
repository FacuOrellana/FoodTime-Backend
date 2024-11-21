package foodtime.app.common;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReactAdminController<DTO, Key> {

    ResponseEntity<List<DTO>> getList();

    ResponseEntity<DTO> getOne(Key id);

    ResponseEntity<DTO> create(DTO entity);

    ResponseEntity<DTO> delete(Key id);

    ResponseEntity<DTO> update(Key id, DTO entity);
}
