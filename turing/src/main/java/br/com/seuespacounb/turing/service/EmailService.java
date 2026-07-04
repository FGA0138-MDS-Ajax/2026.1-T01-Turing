//package br.com.seuespacounb.turing.service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//    private final TemplateEngine templateEngine;
//
//    @Value("${spring.mail.username}")
//    private String emailRemetente;
//
//    public void enviarEmailHtml(String destinatario, String assunto, String nomeTemplate, Context context) {
//        try {
//            // processa o template HTML com as variáveis do Context
//            String conteudoHtml = templateEngine.process(nomeTemplate, context);
//
//            MimeMessage mensagem = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
//
//            helper.setFrom(emailRemetente);
//            helper.setTo(destinatario);
//            helper.setSubject(assunto);
//            helper.setText(conteudoHtml, true); // true = é HTML
//
//            mailSender.send(mensagem);
//
//        } catch (MessagingException e) {
//            throw new RuntimeException("Erro ao enviar email para: " + destinatario, e);
//        }
//    }
//}

package br.com.seuespacounb.turing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final TemplateEngine templateEngine;

    @Value("${resend.api-key}")
    private String apiKey;

    @Value("${resend.sender-email}")
    private String senderEmail;

    @Value("${resend.sender-name}")
    private String senderName;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public void enviarEmailHtml(String destinatario, String assunto, String nomeTemplate, Context context) {
        try {
            String conteudoHtml = templateEngine.process(nomeTemplate, context);

            Map<String, Object> body = Map.of(
                    "from", senderName + " <" + senderEmail + ">",
                    "to", List.of(destinatario),
                    "subject", assunto,
                    "html", conteudoHtml
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Falha ao enviar email via Resend para " + destinatario + ": " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email para: " + destinatario, e);
        }
    }
}