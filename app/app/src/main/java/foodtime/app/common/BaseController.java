package foodtime.app.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

public abstract class BaseController<T, K> implements ReactAdminController<T, K> {

    private final CrudService<T, K> crudService;

    public BaseController(CrudService<T, K> crudService) {
        this.crudService = Objects.requireNonNull(crudService);
    }


    @GetMapping
    public ResponseEntity<List<T>> getList() {
        List<T> response = crudService.findAll();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable(value = "id") K id) {
        return crudService.findOne(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
    }

    @Override
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        return ResponseEntity.ok(crudService.create(entity));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable(value = "id") K id) {
        return ResponseEntity.ok(crudService.delete(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable(value = "id") K id, @RequestBody T entity) {
        return crudService.findOne(id)
                .map(e -> {
                    crudService.update(id, entity);
                    return ResponseEntity.ok(entity);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
    }
}
