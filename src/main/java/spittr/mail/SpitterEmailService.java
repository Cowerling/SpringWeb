package spittr.mail;

import spittr.Spittle;

import javax.mail.MessagingException;

public interface SpitterEmailService {
    void sendSimpleSpittleEmail(String to, Spittle spittle);
    void sendSpittleEmailWithAttachment(String to, Spittle spittle) throws MessagingException;
    void sendRichSpittleEmail(String to, Spittle spittle) throws MessagingException;
    void sendRichSpittleEmailWithTemplate(String to, Spittle spittle) throws MessagingException;
}
