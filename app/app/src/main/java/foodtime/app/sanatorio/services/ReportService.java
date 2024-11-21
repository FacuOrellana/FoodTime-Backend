package foodtime.app.sanatorio.services;

import foodtime.app.sanatorio.repositories.InsumoRepository;
import foodtime.app.sanatorio.repositories.PedidoRepository;
import foodtime.app.sanatorio.repositories.projections.EmpleadosPedidos;
import foodtime.app.sanatorio.repositories.projections.InsumosPorFecha;
import foodtime.app.sanatorio.repositories.projections.PedidosByPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {

    private final PedidoRepository pedidoRepository;
    private final InsumoRepository insumoRepository;

@Autowired
public ReportService(PedidoRepository pedidoRepository, InsumoRepository insumoRepository) {
    this.pedidoRepository = pedidoRepository;
    this.insumoRepository = insumoRepository;
}

public List<PedidosByPersona> getPedidosByPersona(Integer personaId){
    return pedidoRepository.getPedidosByPersona(personaId);

}

    public List<PedidosByPersona> getPedidosByDni(String dni){
        return pedidoRepository.getPedidosByDni(dni);

    }

    public List<InsumosPorFecha> getInsumosPorFecha(String fechaInicio, String fechaFin) {
            return insumoRepository.getInsumosPorFecha(fechaInicio, fechaFin);
    }

    public List<EmpleadosPedidos> getEmpleadosPedidos() {
        return pedidoRepository.getEmpleadosPedidos();
    }


}
