package com.etl.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class EmailService {
    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) {
        try {
            // Create an EmailAttachment
            EmailAttachment emailAttachment = new EmailAttachment();
            emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
            emailAttachment.setDescription("Attachment");
            emailAttachment.setName(attachmentName);

            // Create a temporary file from the byte array
            File tempFile = File.createTempFile("tempAttachment", null);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(attachment);
            }

            // Set the path to the temporary file in EmailAttachment
            emailAttachment.setPath(tempFile.getAbsolutePath());

            // Create the email
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com"); // Set your SMTP server here
            email.setSmtpPort(587); // Set the SMTP port (587 for TLS)
            email.setAuthenticator(new DefaultAuthenticator("shimeelaskar527@gmail.com", "cpafkwwgzhswdpof")); // Set your Gmail email and password
            email.setStartTLSEnabled(true);
            email.setFrom("shimeelaskar527@gmail.com");
            email.setSubject(subject);
            email.setMsg(text);
            email.addTo(to);

            // Attach the EmailAttachment to the email
            email.attach(emailAttachment);

            // Send the email
            email.send();

            // Delete the temporary file
            tempFile.delete();
        } catch (EmailException | IOException e) {
            e.printStackTrace();
        }
    }
}
