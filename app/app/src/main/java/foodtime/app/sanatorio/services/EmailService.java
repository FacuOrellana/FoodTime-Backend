package foodtime.app.sanatorio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            try {
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, true); // true para habilitar el HTML
                helper.setFrom("sanatoriorivadavia7@gmail.com"); // Asegúrate de que sea el correo desde el que enviarás
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        try {
            javaMailSender.send(preparator);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
