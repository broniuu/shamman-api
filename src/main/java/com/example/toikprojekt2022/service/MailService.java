package com.example.toikprojekt2022.service;

import com.itextpdf.io.source.IRandomAccessSource;
import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailService {
    static String username ="szamannoreply@gmail.com";
    static String password = "ntigzagdmctmnlwz";

    public static void sendEmail(ByteArrayOutputStream pdf , String recipientsField, String titleField, String contentField) throws IOException {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientsField)
            );
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(contentField);
            message.setSubject(titleField);
            MimeBodyPart attachmentPart = new MimeBodyPart();

            ByteArrayDataSource ds=new ByteArrayDataSource(pdf.toByteArray(),"application/pdf");
            attachmentPart.setDataHandler(new DataHandler(ds));
            attachmentPart.setFileName("Receipt.pdf");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart );
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}