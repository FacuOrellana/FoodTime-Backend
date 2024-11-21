package foodtime.app.sanatorio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public enum TipoUsuario {
    ENFERMERO("Enfermero"),
    PACIENTE("Paciente"),
    MEDICO("Medico"),
    ADMINISTRADOR("Administrador"),
    COCINA("Cocina"),
    DIRECTIVO("Directivo");


    @JsonProperty(value = "id")
    private final String name = this.name();
    @JsonProperty(value = "name")
    private final String description;

    TipoUsuario(String value) {
        this.description = value;
    }

    public static TipoUsuario findByName(String name) {
        TipoUsuario result = null;
        for (TipoUsuario tipoUsuario : values()) {
            if ( StringUtils.equalsIgnoreCase( tipoUsuario.name() , name )) {
                result = tipoUsuario;
                break;
            }
        }
        return result;
    }
}
