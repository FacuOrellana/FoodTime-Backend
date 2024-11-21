package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.MenuDto;
import foodtime.app.sanatorio.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends BaseController<MenuDto, Integer> {

    @Autowired
    public MenuController(MenuService menuService) {
        super(menuService);
    }

}
