package com.learning.springreddit.service;

import com.learning.springreddit.exception.SpringRedditException;
import com.learning.springreddit.model.NotificationEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Value("${email}")
    private String emailId;

    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = (mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(emailId);
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(notificationEmail.getBody());
        });

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent !!");
        } catch (MailException e) {
            throw new SpringRedditException("Exception occurred while sending mail to " + notificationEmail.getRecipient());
        }

    }
}
