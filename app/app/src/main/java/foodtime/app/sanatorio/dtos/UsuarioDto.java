package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Usuario;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

public record UsuarioDto(Integer id,
                         @Email String email,
                         String password,
                         String tipoUsuario,
                         PersonaDto personaDto,
                         List<String> permisos,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                         boolean deleted) {
    public UsuarioDto(Usuario usuario, PersonaDto persona, List<String> permisos) {
        this(usuario.getId(),usuario.getEmail(),usuario.getContraseña(),usuario.getTipoUsuario().name(),
                persona,permisos,usuario.getCreatedAt(),usuario.getModifiedAt(), usuario.isDeleted());
    }

}
