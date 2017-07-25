package spittr.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import spittr.Spittle;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SpitterEmailServiceImpl implements SpitterEmailService {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private Pattern pattern = Pattern.compile("\\#(\\S+)");

    @Override
    public void sendSimpleSpittleEmail(String to, Spittle spittle) {
        Matcher matcher = pattern.matcher(spittle.getMessage());
        if (matcher.find()) {
            String toHost = matcher.group(1);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("surpermama@126.com");
            message.setTo(toHost);
            message.setSubject("New spittle from " + spittle.getId());
            message.setText(spittle.getId() + " says: " + spittle.getMessage());
            mailSender.send(message);
        }
    }

    @Override
    public void sendSpittleEmailWithAttachment(String to, Spittle spittle) throws MessagingException {
        Matcher matcher = pattern.matcher(spittle.getMessage());
        if (matcher.find()) {
            String toHost = matcher.group(1);
            MimeMessage message = ((JavaMailSender) mailSender).createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom("surpermama@126.com");
            messageHelper.setTo(toHost);
            messageHelper.setSubject("New spittle from " + spittle.getId());
            messageHelper.setText(spittle.getId() + " says: " + spittle.getMessage());
            messageHelper.addAttachment("", new FileSystemResource("/collateral/coupon.png"));
            ((JavaMailSender) mailSender).send(message);
        }
    }

    @Override
    public void sendRichSpittleEmail(String to, Spittle spittle) throws MessagingException {
        Matcher matcher = pattern.matcher(spittle.getMessage());
        if (matcher.find()) {
            String toHost = matcher.group(1);
            MimeMessage message = ((JavaMailSender) mailSender).createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom("surpermama@126.com");
            messageHelper.setTo(toHost);
            messageHelper.setSubject("New spittle from " + spittle.getId());
            messageHelper.setText("<html><body><img src='cid:spitterLogo'><h4>" + spittle.getId() + " says...</h4><i>" + spittle.getMessage() + "</i></body></html>");
            ClassPathResource image = new ClassPathResource("collateral/spitter_logo_50.png");
            messageHelper.addInline("spitterLogo", image);
            ((JavaMailSender) mailSender).send(message);
        }
    }

    @Override
    public void sendRichSpittleEmailWithTemplate(String to, Spittle spittle) throws MessagingException {
        Matcher matcher = pattern.matcher(spittle.getMessage());
        if (matcher.find()) {
            String toHost = matcher.group(1);
            MimeMessage message = ((JavaMailSender) mailSender).createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom("surpermama@126.com");
            messageHelper.setTo(toHost);
            messageHelper.setSubject("New spittle from " + spittle.getId());
            Context context = new Context();
            context.setVariable("spitterName", spittle.getId());
            context.setVariable("spittleText", spittle.getMessage());
            String emailText = templateEngine.process("emailTemplate.html", context);
            messageHelper.setText(emailText);
            ClassPathResource image = new ClassPathResource("collateral/spitter_logo_50.png");
            messageHelper.addInline("spitterLogo", image);
            ((JavaMailSender) mailSender).send(message);
        }
    }
}
