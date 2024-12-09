package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;

public class CorreoUtil {

    private String correoEnvia = "recuperarutp@gmail.com";
    private String contrasena = "yllhxwcjdnsaqplx";

    public String EnviarCorreoConAdjunto(String asunto, String cuerpo, String correo, boolean correoCc, 
            byte[] archivoAdjunto, String nombreArchivoAdjunto) {
        String result = "";
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);

        try {
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));

            if (correoCc) {
                mail.addRecipient(Message.RecipientType.CC, new InternetAddress(correoEnvia)); // Add CC if needed
            }

            mail.setSubject(asunto);
            mail.addHeader("Content-Type", "text/html; charset=UTF-8");

            MimeBodyPart cuerpoParte = new MimeBodyPart();
            cuerpoParte.setContent(cuerpo, "text/plain");

            MimeBodyPart adjuntoParte = new MimeBodyPart();
            ByteArrayDataSource dataSource = new ByteArrayDataSource(archivoAdjunto, "application/pdf");
            adjuntoParte.setDataHandler(new DataHandler(dataSource));
            adjuntoParte.setFileName(nombreArchivoAdjunto);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoParte);
            multipart.addBodyPart(adjuntoParte);

            mail.setContent(multipart);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia, contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

            result = "OK";
        } catch (Exception ex) {
            result = ex.getMessage();
            ex.printStackTrace();
        }
        return result;
    }
}
