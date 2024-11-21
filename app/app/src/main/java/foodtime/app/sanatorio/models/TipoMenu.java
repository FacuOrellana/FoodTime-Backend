package foodtime.app.sanatorio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public enum TipoMenu {
    DESAYUNO("Desayuno"),
    ALMUERZO("Almuerzo"),
    MERIENDA("Merienda"),
    CENA("Cena");

    @JsonProperty(value = "id")
    private final String name = this.name();
    @JsonProperty(value = "name")
    private final String description;

    TipoMenu(String value) {
        this.description = value;
    }

    public static TipoMenu findByName(String name) {
        TipoMenu result = null;
        for (TipoMenu tipoMenu : values()) {
            if ( StringUtils.equalsIgnoreCase( tipoMenu.name() , name )) {
                result = tipoMenu;
                break;
            }
        }
        return result;
    }
}
