package foodtime.app.sanatorio.services;


import foodtime.app.common.BaseRepository;
import foodtime.app.common.BaseService;
import foodtime.app.sanatorio.dtos.PedidoDto;
import foodtime.app.sanatorio.dtos.PedidoExtraDto;
import foodtime.app.sanatorio.dtos.PedidoMenuDto;
import foodtime.app.sanatorio.models.*;
import foodtime.app.sanatorio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService extends BaseService<Pedido, PedidoDto, Integer> {

    private final PedidoRepository pedidoRepository;
    private final PersonaRepository personaRepository;
    private final PedidoMenuRepository pedidoMenuRepository;
    private final PedidoMenuService pedidoMenuService;
    private final PedidoExtraRepository pedidoExtraRepository;
    private final PedidoExtraService pedidoExtraService;
    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PersonaRepository personaRepository, PedidoMenuRepository pedidoMenuRepository,
                         PedidoMenuService pedidoMenuService, PedidoExtraRepository extraRepository, PedidoExtraService pedidoExtraService, EmailService emailService, UsuarioRepository usuarioRepository){
        this.pedidoRepository = pedidoRepository;
        this.personaRepository = personaRepository;
        this.pedidoMenuRepository = pedidoMenuRepository;
        this.pedidoMenuService = pedidoMenuService;
        this.pedidoExtraRepository = extraRepository;
        this.pedidoExtraService = pedidoExtraService;
        this.emailService = emailService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected BaseRepository<Pedido, Integer> getRepository() {
        return pedidoRepository;
    }

    @Override
    protected PedidoDto toDTO(Pedido entity) {
        List<PedidoMenu> pedidoMenuList = pedidoMenuRepository.findAllByPedidoIdAndDeletedIsFalse(entity.getId());
        PedidoExtra pedidoExtra = pedidoExtraRepository.findByPedidoId(entity.getId());
        PedidoExtraDto pedidoExtraDto = null;
        if (pedidoExtra != null) {
            pedidoExtraDto = pedidoExtraService.toDTO(pedidoExtra);
        }
        return new PedidoDto(entity, pedidoMenuList, pedidoExtraDto);
    }


    @Override
    protected Pedido toEntity(PedidoDto entity) {
        Pedido pedido = new Pedido(entity);
        if(entity.personaId() != null) {
            Persona persona = personaRepository.findById(entity.personaId()).orElseThrow();
            pedido.setPersona(persona);
        }
        pedido.setPedidoMenus( entity.pedidoMenuList() == null ? Collections.emptySet() :
                entity.pedidoMenuList().stream().map(PedidoMenu::new).collect(Collectors.toSet()));
        return pedido;
    }

    @Override
    @Transactional
    public PedidoDto create(PedidoDto dto) {
        Pedido pedido = toEntity(dto);
        Pedido savedPedido = pedidoRepository.save(pedido);
        List<Integer> menuIds = dto.pedidoMenuList().stream()
                .map(PedidoMenuDto::menuId)
                .toList();
        pedidoMenuService.createPedidosMenu(savedPedido.getPedidoMenus(),savedPedido,menuIds);
        if (dto.extra() !=null ){
            pedidoExtraService.createPedidoExtra(savedPedido,dto.extra());
        }
        Usuario usuario = usuarioRepository.findByPersona(pedido.getPersona());
        String email = usuario.getEmail();
        if(usuario == null){
            email = dto.extra().email();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
        String fechaFormateada = formatter.format(pedido.getCreatedAt());
        String plantillaEmailPedidoPendiente = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Confirmación de Pedido</title>\n" +
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
                "      background-color: #FFA500;\n" +
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
                "      background-color: #FFA500;\n" +
                "      color: white;\n" +
                "      padding: 10px 20px;\n" +
                "      border-radius: 5px;\n" +
                "      text-decoration: none;\n" +
                "      font-weight: bold;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    .button:hover {\n" +
                "      background-color: #e69500;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"email-container\">\n" +
                "    <div class=\"email-header\">\n" +
                "      <h2>¡Pedido Creado con Éxito!</h2>\n" +
                "    </div>\n" +
                "    <div class=\"email-body\">\n" +
                "      <p>Hola,</p>\n" +
                "      <p>Tu pedido ha sido creado exitosamente y se encuentra en estado <strong>pendiente</strong>. Estamos procesando la información y te notificaremos en cuanto sea aprobado para ser preparado.</p>\n" +
                "      <div class=\"pedido-detalles\">\n" +
                "        <p><strong>Número de pedido:</strong>"+ pedido.getId()+"</p>\n" +
                "        <p><strong>Fecha:</strong>"+ fechaFormateada +"</p>\n" +
                "      </div>\n" +
                "      <p>Mientras tanto, puedes revisar el estado de tu pedido ingresando a nuestro sistema:</p>\n" +
                "      <p style=\"text-align: center;\">\n" +
                "        <a href=\"http://localhost:3000/\" class=\"button\">Ingresar</a>\n" +
                "      </p>\n" +
                "      <p>Gracias por confiar en nosotros.</p>\n" +
                "      <p>Saludos cordiales,</p>\n" +
                "      <p>El equipo de FoodTime</p>\n" +
                "    </div>\n" +
                "    <div class=\"email-footer\">\n" +
                "      <p>Para más información, contáctanos al correo: <a href=\"mailto:support@foodtime.com\">support@foodtime.com</a></p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        emailService.sendEmail(email,"Pedido creado exitosamente",plantillaEmailPedidoPendiente);

        return toDTO(savedPedido);
    }

    @Override
    @Transactional
    public PedidoDto update(Integer pedidoId, PedidoDto dto) {
        Pedido entityToUpdate = toEntity(dto);
        entityToUpdate.setId(pedidoId);
        Pedido savedEntity = getRepository().save(entityToUpdate);
        pedidoMenuService.updatePedidosMenu(dto.pedidoMenuList(),savedEntity);
        return toDTO(savedEntity);
    }

    public PedidoDto cambiarEstado(Integer id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.setEstadoPedido(EstadoPedido.findByName(nuevoEstado));
        pedidoRepository.save(pedido);
        return toDTO(pedido);
    }


}
