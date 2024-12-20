package foodtime.app.sanatorio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public enum MetodoPago {
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    CUENTA("Cuenta Corriente"),
    DIETA("Dieta");

    @JsonProperty(value = "id")
    private final String name = this.name();
    @JsonProperty(value = "name")
    private final String description;

    MetodoPago(String value) {
        this.description = value;
    }

    public static MetodoPago findByName(String name) {
        MetodoPago result = null;
        for (MetodoPago metodoPago : values()) {
            if ( StringUtils.equalsIgnoreCase( metodoPago.name() , name )) {
                result = metodoPago;
                break;
            }
        }
        return result;
    }
}
