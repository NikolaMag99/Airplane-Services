package SKProjekat2.Servis3.controllers;


import SKProjekat2.Servis3.entities.Card;
import SKProjekat2.Servis3.forms.FlightFormPlane;
import SKProjekat2.Servis3.forms.UpdateUserMilesForm;
import SKProjekat2.Servis3.forms.UserMilesForm;
import SKProjekat2.Servis3.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("")
public class Controller {

    private CardRepository cardRepo;

    @Autowired
    public Controller(CardRepository cardRepo) {
        super();
        this.cardRepo = cardRepo;
    }

    private String toStringCards(List<Card> tickets) {
        StringBuilder sb = new StringBuilder();
        for (Card c : tickets) {
            sb.append(c.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    @PostMapping("/payment")
    public ResponseEntity<String> cardPayment(@RequestHeader(value = "Authorization") String token, @RequestBody FlightFormPlane form) {
        try {
            System.out.println("enter!");
            long flightid = form.getFlightId();
            int planeCapacity = form.getCapacity();
            int currCapacity = cardRepo.countCardsWithFlights(flightid);
            System.out.println(currCapacity + " " + planeCapacity);

            if (currCapacity < planeCapacity) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders httpHeaders = new HttpHeaders();
                int miles = form.getDuration();
                UpdateUserMilesForm updateUserMilesForm = new UpdateUserMilesForm(miles, token);
                HttpEntity<UpdateUserMilesForm> httpEntity = new HttpEntity<>(updateUserMilesForm, httpHeaders);
                ResponseEntity<UserMilesForm> answer = restTemplate.exchange("http://localhost:8080/obtainUserMiles", HttpMethod.POST, httpEntity, UserMilesForm.class);
                System.out.println("whole usermiles\t" + answer.getBody().toString());
                int sale = 0;
                int userMiles = answer.getBody().getMiles();
                long userId = answer.getBody().getUserId();
                System.out.println("userId:\t" + userId);
                if (userMiles > 10000) {
                    sale = 20;
                } else if (userMiles > 1000) {
                    sale = 10;
                } else {
                    sale = 0;
                }
                System.out.println(flightid);
                Card card = new Card(flightid, userId, sale);
                cardRepo.saveAndFlush(card);
                return new ResponseEntity<String>("successful payment", HttpStatus.OK);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> listCards(@RequestHeader(value = "Authorization") String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            int miles = 0;
            UpdateUserMilesForm updateUserMilesForm = new UpdateUserMilesForm(miles, token);
            HttpEntity<UpdateUserMilesForm> httpEntity = new HttpEntity<>(updateUserMilesForm, httpHeaders);
            ResponseEntity<Long> answer = restTemplate.exchange("http://localhost:8080/obtainUser", HttpMethod.POST, httpEntity, Long.class);
            List<Card> tickets = cardRepo.findCardsOfUser(answer.getBody());
            String l = this.toStringCards(tickets);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/obtainCards")
    public ResponseEntity<List<Long>> cardsByFlight(@RequestBody Long form) {
        try {
            System.out.println(form.toString());
            List<Long> usersid = cardRepo.getAllUsers(form);
            return new ResponseEntity<List<Long>>(usersid, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Long>>(HttpStatus.BAD_GATEWAY);
        }
    }
}
