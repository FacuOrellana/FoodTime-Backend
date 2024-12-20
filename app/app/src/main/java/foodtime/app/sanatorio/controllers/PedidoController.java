package foodtime.app.sanatorio.controllers;

import foodtime.app.common.BaseController;
import foodtime.app.sanatorio.dtos.PedidoDto;
import foodtime.app.sanatorio.models.Pedido;
import foodtime.app.sanatorio.models.PedidoExtra;
import foodtime.app.sanatorio.models.Persona;
import foodtime.app.sanatorio.models.Usuario;
import foodtime.app.sanatorio.repositories.PedidoExtraRepository;
import foodtime.app.sanatorio.repositories.PedidoRepository;
import foodtime.app.sanatorio.repositories.PersonaRepository;
import foodtime.app.sanatorio.repositories.UsuarioRepository;
import foodtime.app.sanatorio.services.EmailService;
import foodtime.app.sanatorio.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController extends BaseController<PedidoDto, Integer> {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;
    private final PedidoExtraRepository pedidoExtraRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository, EmailService emailService, UsuarioRepository usuarioRepository, PedidoExtraRepository pedidoExtraRepository, PersonaRepository personaRepository) {
        super(pedidoService);
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;
        this.pedidoExtraRepository = pedidoExtraRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    @PutMapping("/cambiarestado/{id}")
    public ResponseEntity<PedidoDto> updateEstadoPedido(@PathVariable(value = "id") Integer id, @RequestBody String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        Integer personaId = pedidoRepository.getPersonaIdByPedidoId(id);
        Persona persona = personaRepository.findById(personaId).orElse(null);
        String emailAddress;
        if(pedido.getPersona().getId() != null){
            Usuario usuario = usuarioRepository.findByPersona(persona);
            emailAddress = usuario.getEmail();
        }else{
            PedidoExtra pedidoExtra = pedidoExtraRepository.findByPedidoId(id);
            emailAddress = pedidoExtra.getEmail();
        }
        if(pedido.getEstadoPedido().name() == "PENDIENTE" && nuevoEstado.equalsIgnoreCase("PREPARACION")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
            String fechaFormateada = formatter.format(pedido.getCreatedAt());
            String plantillaEmailPedidoEnPreparacion = "<!DOCTYPE html>\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <title>Actualización de Pedido</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: Arial, sans-serif;\n" +
                    "      background-color: #e0e0e0;\n" +
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
                    "    .pedido-detalles {\n" +
                    "      background-color: #f9f9f9;\n" +
                    "      padding: 15px;\n" +
                    "      border: 1px solid #ddd;\n" +
                    "      border-radius: 5px;\n" +
                    "      margin: 10px 0;\n" +
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
                    "      <h2>¡Tu pedido esta siendo preparado!</h2>\n" +
                    "    </div>\n" +
                    "    <div class=\"email-body\">\n" +
                    "      <p>Hola,</p>\n" +
                    "      <p>Nos complace informarte que tu pedido ahora está en estado <strong>preparación</strong>. Nuestro equipo está trabajando para que todo esté listo lo antes posible.</p>\n" +
                    "      <div class=\"pedido-detalles\">\n" +
                    "        <p><strong>Número de pedido:</strong>"+ pedido.getId()+"</p>\n" +
                    "        <p><strong>Fecha:</strong>"+ fechaFormateada +"</p>\n" +
                    "      </div>\n" +
                    "      <p>Puedes seguir revisando el estado de tu pedido a través de nuestro sistema:</p>\n" +
                    "      <p style=\"text-align: center;\">\n" +
                    "        <a href=\"http://200.123.110.132:16156/\" class=\"button\">Ingresar</a>\n" +
                    "      </p>\n" +
                    "      <p>Gracias por confiar en nosotros. Estamos a tu disposición para cualquier duda o consulta.</p>\n" +
                    "      <p>Saludos cordiales,</p>\n" +
                    "      <p>El equipo de FoodTime</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"email-footer\">\n" +
                    "      <p>Para más información, contáctanos al correo: <a href=\"mailto:support@foodtime.com\">support@foodtime.com</a></p>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>";

            emailService.sendEmail(emailAddress,"Pedido en preparacion",plantillaEmailPedidoEnPreparacion);

        }
        return ResponseEntity.ok(pedidoService.cambiarEstado(id,nuevoEstado));
    }

}
