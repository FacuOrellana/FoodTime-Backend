package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.LoginRequestDto;
import foodtime.app.sanatorio.dtos.UsuarioDto;
import foodtime.app.sanatorio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController extends BaseController<UsuarioDto, Integer> {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto entity) {
        return usuarioService.login(entity);
    }

    @Transactional
    @PostMapping("/recuperar-contraseña")
    public ResponseEntity<?> recuperarContraseña(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return usuarioService.recuperarContraseña(email);
    }

    @Transactional
    @PostMapping("/nueva-contraseña")
    public ResponseEntity<?> nuevaContraseña(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String nuevaContraseña = data.get("nuevaContraseña");
        return usuarioService.nuevaContraseña(userId, nuevaContraseña);
    }






}