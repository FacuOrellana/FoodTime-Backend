package foodtime.app.sanatorio.job;

import foodtime.app.sanatorio.repositories.DietaMenuRepository;
import foodtime.app.sanatorio.repositories.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class DietasToPedidosJob {

    private final DietaMenuRepository dietaMenuRepository;
    private final DietaRepository dietaRepository;

    @Autowired
    public DietasToPedidosJob(DietaMenuRepository dietaMenuRepository, DietaRepository dietaRepository){
        this.dietaMenuRepository = dietaMenuRepository;
        this.dietaRepository = dietaRepository;
    }

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void createPedidosFromDietas() throws IOException {
        System.out.println("Log del scheduled job");
    }


}
