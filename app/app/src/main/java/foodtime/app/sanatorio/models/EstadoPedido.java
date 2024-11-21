package foodtime.app.sanatorio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    PREPARACION("Preparacion"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    @JsonProperty(value = "id")
    private final String name = this.name();
    @JsonProperty(value = "name")
    private final String description;

    EstadoPedido(String value) {
        this.description = value;
    }

    public static EstadoPedido findByName(String name) {
        EstadoPedido result = null;
        for (EstadoPedido estadoPedido : values()) {
            if ( StringUtils.equalsIgnoreCase( estadoPedido.name() , name )) {
                result = estadoPedido;
                break;
            }
        }
        return result;
    }
}