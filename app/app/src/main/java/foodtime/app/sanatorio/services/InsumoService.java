package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.InsumoDto;
import foodtime.app.sanatorio.models.Insumo;
import foodtime.app.sanatorio.repositories.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsumoService extends BaseService<Insumo, InsumoDto, Integer> {

    private final InsumoRepository insumoRepository;

    @Autowired
    public InsumoService(InsumoRepository insumoRepository){
        this.insumoRepository = insumoRepository;
    }

    @Override
    protected BaseRepository<Insumo, Integer> getRepository() {
        return insumoRepository;
    }

    @Override
    protected InsumoDto toDTO(Insumo entity) {
        return new InsumoDto(entity);
    }

    @Override
    protected Insumo toEntity(InsumoDto entity) {
        return new Insumo(entity);
    }
}
