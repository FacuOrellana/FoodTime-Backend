package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.PersonaDto;
import foodtime.app.sanatorio.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/personas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonaController extends BaseController<PersonaDto, Integer> {

    @Autowired
    public PersonaController(PersonaService personaService) {
        super(personaService);
    }

}
