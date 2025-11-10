package com.phuc.notification.service.Impl;

import com.phuc.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.phuc.notification.dto.request.SendEmailRequest;
import com.phuc.notification.dto.response.EmailResponse;
import com.phuc.notification.exception.AppException;
import com.phuc.notification.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {

      JavaMailSender mailSender;

      @Value("${spring.mail.username}")
      @NonFinal
      String fromEmail;

      @Value("${notification.email.from-name}")
      @NonFinal
      String fromName;

      public EmailResponse sendEmail(SendEmailRequest request) {
            try {
                  String email = Objects.requireNonNull(fromEmail, "Email sender address is required");
                  String name = Objects.requireNonNull(fromName, "Email sender name is required");
                  
                  log.debug("Sending email from: {} ({})", email, name);
                  
                  MimeMessage mimeMessage = mailSender.createMimeMessage();
                  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                  
                  helper.setFrom(email, name);
                  helper.setTo(Objects.requireNonNull(request.getTo().getEmail(), "Recipient email is required"));
                  helper.setSubject(Objects.requireNonNull(request.getSubject(), "Email subject is required"));
                  helper.setText(Objects.requireNonNull(request.getHtmlContent(), "Email content is required"), true); // true indicates HTML content
                  
                  mailSender.send(mimeMessage);
                  
                  log.info("Email sent successfully to: {}", request.getTo().getEmail());
                  
                  return EmailResponse.builder()
                          .messageId("email-" + System.currentTimeMillis())
                          .build();
                          
            } catch (MessagingException e) {
                  log.error("Error sending email to {}: {}", request.getTo().getEmail(), e.getMessage(), e);
                  throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
            } catch (Exception e) {
                  log.error("Unexpected error sending email to {}: {}", request.getTo().getEmail(), e.getMessage(), e);
                  throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
            }
      }

}