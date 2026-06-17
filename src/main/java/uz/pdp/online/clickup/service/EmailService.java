package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendAsync(String to, String subject, String text) {
        try {
            log.debug("Sending email to: {}", to);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);

            log.info("Email successfully sent to: {}", to);
        } catch (Exception e) {
            // Email failure should not disrupt the core business logic — log only!
            log.error("Error sending email: to={}, error={}", to, e.getMessage(), e);
        }
    }

    @Async
    public void sendVerificationCode(String to, String code) {
        sendAsync(
                to,
                "Email Verification Code — ClickUp",
                "Your verification code is: " + code
        );
    }

    @Async
    public void sendWorkspaceInvitation(String to, String workspaceName, String inviteLink) {
        String text = String.format(
                "You have been invited to the '%s' workspace.%nTo join, click the following link: %s",
                workspaceName, inviteLink
        );
        sendAsync(to, "Workspace Invitation — ClickUp", text);
    }
}
