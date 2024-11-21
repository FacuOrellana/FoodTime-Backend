package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.InsumoDto;
import foodtime.app.sanatorio.services.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/insumos", produces = MediaType.APPLICATION_JSON_VALUE)
public class InsumoController extends BaseController<InsumoDto, Integer> {

    @Autowired
    public InsumoController(InsumoService insumoService) {
        super(insumoService);
    }

}
