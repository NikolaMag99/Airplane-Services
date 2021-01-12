package SKProjekat2.Servis1.controllers;


import SKProjekat2.Servis1.Entites.CreditCard;
import SKProjekat2.Servis1.Entites.User;
import SKProjekat2.Servis1.forms.*;
import SKProjekat2.Servis1.listener.Consumer;
import SKProjekat2.Servis1.repository.CreditCardRepository;
import SKProjekat2.Servis1.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("")
public class Registration {

    private UserRepository userRepository;
    private CreditCardRepository creditCardRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Consumer consumer;

    @Autowired
    public Registration(UserRepository userRepository, BCryptPasswordEncoder encoder, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> post(@RequestBody RegistrationForm registrationForm) {
        try {
            System.out.println("usao");
            User user = new User(registrationForm.getName(), registrationForm.getSurName(),
                    registrationForm.getEmail(), encoder.encode(registrationForm.getPassword()), registrationForm.getPassportNumber());
            userRepository.saveAndFlush(user);
            consumer.sendMail(user.getEmail());
            return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/editUserProfile")
    public ResponseEntity<String> editUserProfile(@RequestHeader(value = "Authorization") String token, @RequestBody EditUserForm editUserForm) {

        try {
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();
            String oldEmail = email;
            User user = userRepository.findByEmail(email);
            user.setName(editUserForm.getName());
            user.setSurName(editUserForm.getSurName());
            user.setEmail(editUserForm.getEmail());
            user.setPassword(encoder.encode(editUserForm.getPassword()));
            user.setPassportNumber(editUserForm.getPassportNumber());
            if (!oldEmail.equals(email)) {
                consumer.sendMail(user.getEmail());

            }
            userRepository.saveAndFlush(user);
            return new ResponseEntity<String>(user.getName() + " " + user.getSurName(), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/whoAmI")
    public ResponseEntity<String> whoAmI(@RequestHeader(value = "Authorization") String token) {
        try {

            // izvlacimo iz tokena subject koj je postavljen da bude email
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();

            User user = userRepository.findByEmail(email);

            return new ResponseEntity<>(user.getName() + " " + user.getSurName(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/obtainUserMiles")
    public ResponseEntity<UserMilesForm> searchFlights(@RequestBody UpdateUserMilesForm form) {
        try {

            String token = form.getToken();
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();
            int miles = form.getMiles();
            User user = userRepository.findByEmail(email);
            user.setMiles(user.getMiles() + miles);
            userRepository.saveAndFlush(user);
            UserMilesForm userMiles = new UserMilesForm((long) user.getId(), user.getMiles());
            System.out.println(user.toString());
            System.out.println(userMiles.toString());
            return new ResponseEntity<UserMilesForm>(userMiles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UserMilesForm>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/addCardToUser")
    public ResponseEntity<String> addCardToUser(@RequestHeader(value = "Authorization") String token, @RequestBody AddCreditCardForm addCreditCardForm) {
        try {
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();

            User user = userRepository.findByEmail(email);
            String cardName = addCreditCardForm.getCardName();
            int cardNumber = addCreditCardForm.getCardNumber();
            int securityCode = addCreditCardForm.getSecurityCode();

            CreditCard creditCard = new CreditCard(cardName, cardNumber, securityCode, user);
            creditCardRepository.save(creditCard);
            return new ResponseEntity<String>(user.getName() + " " + user.getSurName(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/addFlight")
//    public ResponseEntity<String> addFlight(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm form) {
//        try {
//            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
//                    .verify(token.replace("login ", "")).getSubject();
//            // da li je admin
//            if (!email.equals("admin@raf.rs")) {
//                throw new Exception();
//            }
//            HttpHeaders httpHeaders = new HttpHeaders();
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<FlightForm> httpEntity = new HttpEntity<>(form, httpHeaders);
//            ResponseEntity<String> answer = restTemplate.exchange("http://localhost:8081/addFlight", HttpMethod.POST, httpEntity, String.class);
//            return answer;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/checkAdmin")
    public ResponseEntity<Boolean> addPlane(@RequestHeader(value = "Authorization") String token) {
        try {

            // izvlacimo iz tokena subject koj je postavljen da bude email
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();


            if (!email.equals("admin@raf.rs")) {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/searchFlights")
    public ResponseEntity<String> searchFlights(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm form) {
        try {
            String email = JWT.require(Algorithm.HMAC512("SKProjekat2".getBytes())).build()
                    .verify(token.replace("login ", "")).getSubject();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<FlightForm> httpEntity = new HttpEntity<>(form, httpHeaders);
            ResponseEntity<String> answer = restTemplate.exchange("http://localhost:8081/searchFlights", HttpMethod.POST, httpEntity, String.class);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

}
