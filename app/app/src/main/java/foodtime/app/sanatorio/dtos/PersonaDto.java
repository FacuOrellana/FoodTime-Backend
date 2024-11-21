package foodtime.app.sanatorio.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import foodtime.app.sanatorio.models.Persona;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonaDto(Integer id,
                         @NotNull(message = "Internal ID is mandatory") String dni,
                         String nombre,
                         String apellido,
                         @Email String email,
                         String numeroTelefono,
                         String direccion,
                         @JsonFormat(pattern = "dd-MM-yyyy") LocalDate fechaNacimiento,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdAt,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime modifiedAt,
                         boolean deleted) {
    public PersonaDto(Persona persona){
        this(persona.getId(),persona.getDni(), persona.getNombre(), persona.getApellido(), persona.getEmail(),
                persona.getNumeroTelefono(),persona.getDireccion(),persona.getFechaNacimiento(),persona.getCreatedAt(),persona.getModifiedAt(), persona.isDeleted());
    }
}