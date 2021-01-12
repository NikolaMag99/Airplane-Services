package SKProjekat2.Servis1.listener;


import SKProjekat2.Servis1.Entites.User;
import SKProjekat2.Servis1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class Consumer {

    @Autowired
    private UserRepository userRepo;

    @JmsListener(destination = "registrator.queue")
    public void consume(String users) {
        String strings[] = users.split(" ");
        int miles = Integer.parseInt(strings[0]);

        for (int i = 1; i < strings.length; i++) {
            Long userId = Long.parseLong(strings[i]);
            User user = userRepo.getById(userId);
            user.setMiles(user.getMiles() - miles);
            //TODO: Slati mejl
            sendMail(user.getEmail());
        }

    }

    @JmsListener(destination = "cancelflight.queue")
    public void delete(String users) {
        String strings[] = users.split(" ");
        int miles = Integer.parseInt(strings[0]);

        for (int i = 1; i < strings.length; i++) {
            Long userId = Long.parseLong(strings[i]);
            User user = userRepo.getById(userId);
            user.setMiles(user.getMiles() - miles);
            //TODO: Slati mejl
            deleteMail(user.getEmail());
        }

    }

    public void sendMail(String email) {

        String from = "noreply@baeldung.com";
        final String username = "testzaraf@gmail.com";
        final String password = "admintest";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            message.setSubject("Email Verification");

            message.setText("Hi there, please verify your email address");

            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMail(String email) {

        String from = "noreply@baeldung.com";
        final String username = "testzaraf@gmail.com";
        final String password = "admintest";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            message.setSubject("Cancel");

            message.setText("Hi there, your flight has been canceled");

            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
