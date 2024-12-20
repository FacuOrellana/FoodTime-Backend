package foodtime.app.sanatorio.services;

import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.LoginRequestDto;
import foodtime.app.sanatorio.dtos.LoginResponseDto;
import foodtime.app.sanatorio.dtos.PersonaDto;
import foodtime.app.sanatorio.dtos.UsuarioDto;
import foodtime.app.sanatorio.models.Persona;
import foodtime.app.sanatorio.models.TipoUsuario;
import foodtime.app.sanatorio.repositories.PersonaRepository;
import foodtime.app.sanatorio.repositories.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import foodtime.app.sanatorio.models.Usuario;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UsuarioService extends BaseService<Usuario, UsuarioDto, Integer> {

    private final UsuarioRepository usuarioRepository;
    private final PersonaService personaService;
    private final PersonaRepository personaRepository;
    private final EmailService emailService;

    private final Map<TipoUsuario, List<String>> permisosPorTipoUsuario = new HashMap<>();

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PersonaService personaService, PersonaRepository personaRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.personaService = personaService;
        this.personaRepository = personaRepository;
        this.emailService = emailService;

        permisosPorTipoUsuario.put(TipoUsuario.ADMINISTRADOR, Arrays.asList("PEDIDOS", "USUARIOS", "DIETAS", "REPORTES", "COCINA" ));
        permisosPorTipoUsuario.put(TipoUsuario.ENFERMERO, Arrays.asList("PEDIDOS", "DIETAS"));
        permisosPorTipoUsuario.put(TipoUsuario.MEDICO, Arrays.asList("PEDIDOS","DIETAS"));
        permisosPorTipoUsuario.put(TipoUsuario.PACIENTE, Arrays.asList("PEDIDOS"));
        permisosPorTipoUsuario.put(TipoUsuario.COCINA, Arrays.asList("PEDIDOS","REPORTES","COCINA"));
    }

    @Override
    protected BaseRepository<Usuario, Integer> getRepository() {
        return usuarioRepository;
    }

    @Override
    protected UsuarioDto toDTO(Usuario entity) {
        PersonaDto personaDto = personaService.toDTO(entity.getPersona());
        List<String> permisos = obtenerPermisosPorTipoUsuario(entity.getTipoUsuario());
        return new UsuarioDto(entity,personaDto,permisos);
    }

    @Override
    protected Usuario toEntity(UsuarioDto dto) {
        return new Usuario(dto);
    }

    @Override
    @Transactional
    public UsuarioDto create(UsuarioDto dto) {
        Usuario usuario = toEntity(dto);
        Persona persona = personaService.toEntity(dto.personaDto());
        Persona existingPersonaByDni = personaRepository.findByDni(persona.getDni());
        Optional<Usuario> existingUsuarioByEmail = usuarioRepository.findByEmail(dto.email());
        if (existingPersonaByDni != null || existingUsuarioByEmail.isPresent()) {
            throw new EntityExistsException("Ya existe un usuario para el dni o email ingresado");
        };
        Persona savedPersona = personaRepository.save(persona);
        String contraseñaHasheada = BCrypt.hashpw(dto.password(), BCrypt.gensalt());
        usuario.setPersona(savedPersona);
        usuario.setContraseña(contraseñaHasheada);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        String plantillaEmailBienvenida = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Bienvenido a FoodTime</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f8f4f3;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    .email-container {\n" +
                "      width: 100%;\n" +
                "      max-width: 600px;\n" +
                "      margin: 20px auto;\n" +
                "      background-color: white;\n" +
                "      padding: 20px;\n" +
                "      border-radius: 8px;\n" +
                "      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "    }\n" +
                "    .email-header {\n" +
                "      text-align: center;\n" +
                "      background-color: #4CAF50;\n" +
                "      color: white;\n" +
                "      padding: 20px;\n" +
                "      border-radius: 8px 8px 0 0;\n" +
                "    }\n" +
                "    .email-body {\n" +
                "      font-size: 16px;\n" +
                "      color: #333;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    .email-footer {\n" +
                "      font-size: 12px;\n" +
                "      color: #777;\n" +
                "      text-align: center;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    .button {\n" +
                "      display: inline-block;\n" +
                "      background-color: #4CAF50;\n" +
                "      color: white;\n" +
                "      padding: 10px 20px;\n" +
                "      border-radius: 5px;\n" +
                "      text-decoration: none;\n" +
                "      font-weight: bold;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    .button:hover {\n" +
                "      background-color: #45a049;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"email-container\">\n" +
                "    <div class=\"email-header\">\n" +
                "      <h2>¡Bienvenido a FoodTime!</h2>\n" +
                "    </div>\n" +
                "    <div class=\"email-body\">\n" +
                "      <p>Hola,</p>\n" +
                "      <p>Estamos encantados de darte la bienvenida a nuestra comunidad. En FoodTime, estamos comprometidos en brindarte una experiencia única y adaptada a tus necesidades.</p>\n" +
                "      <p>Para comenzar a explorar y disfrutar de todos los beneficios, simplemente inicia sesión en tu cuenta:</p>\n" +
                "      <p style=\"text-align: center;\">\n" +
                "        <a href=\"http://200.123.110.132:16156/\" class=\"button\">Iniciar sesión</a>\n" +
                "      </p>\n" +
                "      <p>Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos. ¡Esperamos que disfrutes de tu experiencia con nosotros!</p>\n" +
                "      <p>Saludos cordiales,</p>\n" +
                "      <p>El equipo de FoodTime</p>\n" +
                "    </div>\n" +
                "    <div class=\"email-footer\">\n" +
                "      <p>Para más información, contáctanos al correo: <a href=\"mailto:support@foodtime.com\">support@foodtime.com</a></p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        emailService.sendEmail(usuario.getEmail(), "Cuenta creada exitosamente", plantillaEmailBienvenida);
        return toDTO(savedUsuario);
    }

    @Override
    @Transactional
    public UsuarioDto update(Integer usuarioId, UsuarioDto dto) {
        String inputPassword = dto.password(); // Esto debe ser el texto claro
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(EntityNotFoundException::new);
        String currentEncryptedPassword = usuario.getContraseña(); // Contraseña actual encriptada

        // Verificar si se ha proporcionado una nueva contraseña
        if (inputPassword != null && !inputPassword.isEmpty()) {
            // Verificar si la nueva contraseña en texto claro coincide con la contraseña encriptada
            if (BCrypt.checkpw(inputPassword, currentEncryptedPassword)) {
                System.out.println("Contraseña cambiada");
                String newEncryptedPassword = BCrypt.hashpw(inputPassword, BCrypt.gensalt());
                usuario.setContraseña(newEncryptedPassword); // Asignar nueva contraseña encriptada
            } else {
                System.out.println("Contraseña no ha cambiado");
                // Si las contraseñas son iguales, simplemente no cambies la contraseña
                // no es necesario re-asignar currentEncryptedPassword
            }
        } else {
            // Manejo de caso cuando no se proporciona una nueva contraseña
            System.out.println("No se proporcionó una nueva contraseña");
        }


        Usuario entityToUpdate = toEntity(dto);
        entityToUpdate.setContraseña(usuario.getContraseña());
        entityToUpdate.setId(usuarioId);

        personaService.update(dto.personaDto().id(), dto.personaDto());
        Persona existingPersona = personaRepository.findById(dto.personaDto().id())
                .orElseThrow(() -> new EntityNotFoundException("Persona not found"));

        entityToUpdate.setPersona(existingPersona);
        Usuario savedEntity = getRepository().save(entityToUpdate);

        return toDTO(savedEntity);
    }




    public ResponseEntity<?> login(LoginRequestDto entity) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(entity.email());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            boolean passwordMatches = BCrypt.checkpw(entity.password(), usuario.getContraseña());

            if (passwordMatches) {
                // Convertir Usuario a UsuarioDto
                UsuarioDto usuarioDto = toDTO(usuario);

                // Crear una respuesta con el UsuarioDto y el mensaje de éxito
                return ResponseEntity.ok(new LoginResponseDto("Login exitoso", usuarioDto));
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }


    public List<String> obtenerPermisosPorTipoUsuario(TipoUsuario tipoUsuario) {
        return permisosPorTipoUsuario.getOrDefault(tipoUsuario, Collections.emptyList());
    }

    public ResponseEntity<?> recuperarContraseña(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Integer usuarioId = usuarioOptional.get().getId();

            // Crear el contenido del email con el userId
            String plantillaEmail = "<!DOCTYPE html>\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <title>Recuperación de Contraseña</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: Arial, sans-serif;\n" +
                    "      background-color: #f8f4f3;\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "    }\n" +
                    "    .email-container {\n" +
                    "      width: 100%;\n" +
                    "      max-width: 600px;\n" +
                    "      margin: 20px auto;\n" +
                    "      background-color: white;\n" +
                    "      padding: 20px;\n" +
                    "      border-radius: 8px;\n" +
                    "      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                    "    }\n" +
                    "    .email-header {\n" +
                    "      text-align: center;\n" +
                    "      background-color: #f84525;\n" +
                    "      color: white;\n" +
                    "      padding: 20px;\n" +
                    "      border-radius: 8px 8px 0 0;\n" +
                    "    }\n" +
                    "    .email-body {\n" +
                    "      font-size: 16px;\n" +
                    "      color: #333;\n" +
                    "      padding: 20px;\n" +
                    "    }\n" +
                    "    .email-footer {\n" +
                    "      font-size: 12px;\n" +
                    "      color: #777;\n" +
                    "      text-align: center;\n" +
                    "      padding: 20px;\n" +
                    "    }\n" +
                    "    .button {\n" +
                    "      display: inline-block;\n" +
                    "      background-color: #f84525;\n" +
                    "      color: white;\n" +
                    "      padding: 10px 20px;\n" +
                    "      border-radius: 5px;\n" +
                    "      text-decoration: none;\n" +
                    "      font-weight: bold;\n" +
                    "      text-align: center;\n" +
                    "    }\n" +
                    "    .button:hover {\n" +
                    "      background-color: #d93f1f;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"email-container\">\n" +
                    "    <div class=\"email-header\">\n" +
                    "      <h2>Recupera tu contraseña</h2>\n" +
                    "    </div>\n" +
                    "    <div class=\"email-body\">\n" +
                    "      <p>Hola,</p>\n" +
                    "      <p>Recibimos una solicitud para recuperar tu contraseña. Si fuiste tú, por favor haz clic en el siguiente botón para restablecer tu contraseña:</p>\n" +
                    "      <p style=\"text-align: center;\">\n" +
                    "        <a href=\"http://200.123.110.132:16156/nueva-contrasena?id=" + usuarioId + "\" class=\"button\">Recuperar contraseña</a>\n" +
                    "      </p>\n" +
                    "      <p>Si no solicitaste este cambio, por favor ignora este correo.</p>\n" +
                    "      <p>Gracias,</p>\n" +
                    "      <p>El equipo de FoodTime</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"email-footer\">\n" +
                    "      <p>Si no reconoces esta solicitud o tienes dudas, por favor contáctanos al correo: <a href=\"mailto:support@foodtime.com\">support@foodtime.com</a></p>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>";

            // Enviar el email
            emailService.sendEmail(email, "Recuperar contraseña", plantillaEmail);

            return ResponseEntity.ok("Correo enviado exitosamente");
        } else {
            return ResponseEntity.status(404).body("No se encontró usuario con el correo solicitado");
        }
    }

    public ResponseEntity<?> nuevaContraseña(String userId, String nuevaContraseña) {

        Usuario usuario = usuarioRepository.findById(Integer.parseInt(userId)).orElseThrow(EntityNotFoundException::new);

        if(usuario != null) {
            String newEncryptedPassword = BCrypt.hashpw(nuevaContraseña, BCrypt.gensalt());
            usuario.setContraseña(newEncryptedPassword);
            usuarioRepository.save(usuario);
        }
        else{
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok("Contraseña actualizada");
    }



}
