package foodtime.app.sanatorio.controllers;

import foodtime.app.sanatorio.repositories.projections.EmpleadosPedidos;
import foodtime.app.sanatorio.repositories.projections.InsumosPorFecha;
import foodtime.app.sanatorio.repositories.projections.PedidosByPersona;
import foodtime.app.sanatorio.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/reportes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/pedidoByPersona/{id}")
    public ResponseEntity<List<PedidosByPersona>> getPedidosByPersonaReport(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(reportService.getPedidosByPersona(id));
    }

    @GetMapping("/pedidoByDni/{id}")
    public ResponseEntity<List<PedidosByPersona>> getPedidosByDniReport(@PathVariable(value = "id") String dni) {
        return ResponseEntity.ok(reportService.getPedidosByDni(dni));
    }

    @GetMapping("/insumosPorFecha")
    public ResponseEntity<List<InsumosPorFecha>> getInsumosPorFecha(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        List<InsumosPorFecha> insumos = reportService.getInsumosPorFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(insumos);

    }

    @GetMapping("/empleadosPedidos")
    public ResponseEntity<List<EmpleadosPedidos>> getEmpleadosPedidos() {
        List<EmpleadosPedidos> pedidos = reportService.getEmpleadosPedidos();
        return ResponseEntity.ok(pedidos);

    }

}
