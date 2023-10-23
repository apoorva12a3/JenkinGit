package com.etl;

import com.etl.service.EmailService;
import org.apache.commons.mail.EmailAttachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.mockito.Mockito.times;

@SpringBootTest
public class EmailServiceTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailService emailServiceUnderTest;

    @BeforeEach
    public void setup() {
        emailServiceUnderTest = new EmailService();
    }

    @Test
    public void testSendEmailWithAttachment() {
        // Mock the email attachment
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        emailAttachment.setDescription("Attachment");
        emailAttachment.setName("testAttachment");

        try {
            // Create a temporary file for the attachment
            File tempFile = File.createTempFile("testAttachmentFile", "abc");
            Path tempFilePath = tempFile.toPath();
            byte[] attachmentContent = "Test Attachment Content".getBytes();
            Files.write(tempFilePath, attachmentContent);

            // Mock the behavior of the email service
            Mockito.doNothing().when(emailService).sendEmailWithAttachment(
                    "recipient@example.com",
                    "Test Subject",
                    "Test Email Body",
                    attachmentContent,
                    "testAttachment"
            );

            emailServiceUnderTest.sendEmailWithAttachment(
                    "recipient@example.com",
                    "Test Subject",
                    "Test Email Body",
                    attachmentContent,
                    "testAttachment"
            );

            // Verify that the sendEmailWithAttachment method is called once
//            Mockito.verify(emailService, times(1)).sendEmailWithAttachment(
//                "recipient@example.com",
//                "Test Subject",
//                "Test Email Body",
//                attachmentContent,
//                "testAttachment"
//            );

            // Delete the temporary file (cleanup)
//            Files.delete(tempFilePath);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
