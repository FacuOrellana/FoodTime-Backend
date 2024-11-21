package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.PedidoDto;
import foodtime.app.sanatorio.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController extends BaseController<PedidoDto, Integer> {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        super(pedidoService);
        this.pedidoService = pedidoService;
    }

    @Transactional
    @PutMapping("/cambiarestado/{id}")
    public ResponseEntity<PedidoDto> updateEstadoPedido(@PathVariable(value = "id") Integer id, @RequestBody String nuevoEstado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id,nuevoEstado));
    }

}
