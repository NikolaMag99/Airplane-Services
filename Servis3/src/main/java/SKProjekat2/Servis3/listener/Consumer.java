package SKProjekat2.Servis3.listener;


import SKProjekat2.Servis3.entities.Card;
import SKProjekat2.Servis3.forms.UserMilesForm;
import SKProjekat2.Servis3.repository.CardRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import java.util.List;
import java.util.Properties;

@Component
public class Consumer {

    @Autowired
    private CardRepository cardRepo;


    @JmsListener(destination = "cancelcard.queue")
    public void consume(String id) {
        System.out.println("usao u metodu za brisanje!");
        long flightid = Long.valueOf(id);
        List<Card> tickets = cardRepo.getAllCards(flightid);
        cardRepo.deleteAll(tickets);
        System.out.println("Izbrisane karte");

    }

}
