package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.PedidoExtraDto;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.models.PedidoExtra;
import foodtime.app.sanatorio.repositories.PedidoExtraRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoExtraService extends BaseService<PedidoExtra, PedidoExtraDto,Integer> {

    private final PedidoExtraRepository pedidoExtraRepository;

    public PedidoExtraService(PedidoExtraRepository pedidoExtraRepository) {
        this.pedidoExtraRepository = pedidoExtraRepository;
    }

    @Override
    protected BaseRepository<PedidoExtra, Integer> getRepository(){ return pedidoExtraRepository;}

    @Override
    protected PedidoExtraDto toDTO(PedidoExtra entity) {
        return new PedidoExtraDto(entity);
    }

    @Override
    protected PedidoExtra toEntity(PedidoExtraDto entity) {
        return new PedidoExtra(entity);
    }

    public PedidoExtraDto createPedidoExtra (Pedido pedido, PedidoExtraDto dto){
        PedidoExtra pedidoExtra = toEntity(dto);
        pedidoExtra.setPedido(pedido);
        return toDTO(pedidoExtraRepository.save(pedidoExtra));

    }
}
