package foodtime.app.sanatorio.controllers;
import foodtime.app.sanatorio.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendEmail("facundo.ore@hotmail.com", "Asunto del correo", "<h1>Correo en HTML</h1><p>Este es un correo de prueba.</p>");
        return "Correo enviado";
    }
}