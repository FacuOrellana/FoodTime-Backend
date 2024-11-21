package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.DietaDto;
import foodtime.app.sanatorio.services.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/dietas", produces = MediaType.APPLICATION_JSON_VALUE)
public class DietaController extends BaseController<DietaDto, Integer> {

    @Autowired
    public DietaController(DietaService dietaService) {
        super(dietaService);
    }

}
