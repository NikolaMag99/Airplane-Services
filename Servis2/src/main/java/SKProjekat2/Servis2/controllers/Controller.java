package SKProjekat2.Servis2.controllers;


import SKProjekat2.Servis2.entity.Flight;
import SKProjekat2.Servis2.entity.Plane;
import SKProjekat2.Servis2.forms.AddPlaneForm;
import SKProjekat2.Servis2.forms.FlightForm;
import SKProjekat2.Servis2.forms.FlightFormPlane;
import SKProjekat2.Servis2.repository.FlightRepository;
import SKProjekat2.Servis2.repository.PlaneRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jms.Queue;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("")
public class Controller {

    private FlightRepository flightRepo;
    private PlaneRepository planeRepo;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue registratorQueue;

    @Autowired
    Queue cancelCardQueue;

    @Autowired
    Queue cancelFlightQueue;

    @Autowired
    public Controller(FlightRepository flightRepo, PlaneRepository planeRepo) {
        super();
        this.flightRepo = flightRepo;
        this.planeRepo = planeRepo;
    }

    private String toStringFlights(List<Flight> flights) {
        StringBuilder sb = new StringBuilder();
        for (Flight f : flights) {
            sb.append(f.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @GetMapping("/allFlights")
    public ResponseEntity<String> allFlights() {
        try {
            List<Flight> flights = flightRepo.findAll();
            String flight = toStringFlights(flights);
            return new ResponseEntity<String>(flight, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPlane/{planeName}")
    public ResponseEntity<Plane> getPlane(@PathVariable String planeName) {
        try {
            Plane plane = planeRepo.findByName(planeName);
            return new ResponseEntity<Plane>(plane, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Plane>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addFlight")
    public ResponseEntity<String> addFlight(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm form) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);
            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
            ResponseEntity<Boolean> answer = restTemplate.exchange("http://localhost:8080/checkAdmin", HttpMethod.GET, httpEntity, Boolean.class);
            System.out.println(answer);
            if (answer.getBody().equals(true)) {
                String planeName = form.getPlaneName();
                String startPlace = form.getStartPlace();
                String endPlace = form.getEndPlace();
                int flightDuration = form.getFlightDuration();
                int price = form.getPrice();
                Plane plane = planeRepo.findByName(planeName);
                Flight flight = new Flight(plane, startPlace, endPlace, flightDuration, price);
                flightRepo.saveAndFlush(flight);
                return new ResponseEntity<String>(flight.getEndPlace(), HttpStatus.OK);
            } else {
                System.out.println("Nisi admin bato");
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
//            return new ResponseEntity<String>(flight.getEndPlace(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteFlight")
    public ResponseEntity<String> deleteFlight(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm form) {
        try {
            String planeName = form.getPlaneName();
            String startPlace = form.getStartPlace();
            String endPlace = form.getEndPlace();
            Plane plane = planeRepo.findByName(planeName);
            Flight flight = flightRepo.findFlight(plane, startPlace, endPlace);

            RestTemplate restTemplate1 = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);
            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
            ResponseEntity<Boolean> answer = restTemplate1.exchange("http://localhost:8080/checkAdmin", HttpMethod.GET, httpEntity, Boolean.class);

            if (answer.getBody().equals(true)) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<Long> entity = new HttpEntity<Long>(flight.getId(), headers);
                ResponseEntity<Object> res = restTemplate.exchange("http://localhost:8082/obtainCards", HttpMethod.POST, entity, Object.class);

                List<Long> usersid = (List<Long>) res.getBody();
                if (usersid.size() == 0) {
                    flightRepo.delete(flight);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(flight.getFlightDuration());
                    sb.append(' ');
                    for (int i = 0; i < usersid.size(); i++) {
                        sb.append(usersid.get(i));
                        sb.append(' ');
                    }
                    jmsTemplate.convertAndSend(cancelFlightQueue, sb.toString());
                    jmsTemplate.convertAndSend(cancelCardQueue, String.valueOf(flight.getId()));
                }
                return new ResponseEntity<String>("successfully deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }

//            return new ResponseEntity<String>("successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addPlane")
    public ResponseEntity<String> addPlane(@RequestHeader(value = "Authorization") String token, @RequestBody AddPlaneForm addPlaneForm) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);
            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
            ResponseEntity<Boolean> answer = restTemplate.exchange("http://localhost:8080/checkAdmin", HttpMethod.GET, httpEntity, Boolean.class);
            System.out.println(answer);
            if (answer.getBody().equals(true)) {
                String planeName = addPlaneForm.getName();
                int capacity = addPlaneForm.getCapacity();
                Plane plane = new Plane(planeName, capacity);
                planeRepo.saveAndFlush(plane);

                return new ResponseEntity<String>("succes", HttpStatus.OK);
            } else {
                System.out.println("Nisi admin bato");
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }


//            return new ResponseEntity<String>("succes", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deletePlane")
    public ResponseEntity<String> deletePlane(@RequestHeader(value = "Authorization") String token, @RequestBody AddPlaneForm addPlaneForm) {
        try {
            RestTemplate restTemplate1 = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", token);
            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
            ResponseEntity<Boolean> answer = restTemplate1.exchange("http://localhost:8080/checkAdmin", HttpMethod.GET, httpEntity, Boolean.class);
            if (answer.getBody().equals(true)) {
                String planeName = addPlaneForm.getName();
                Plane plane = planeRepo.findByName(planeName);
                List<Flight> flights = flightRepo.findFlightsByPlane(plane);
                if (flights.size() == 0)
                    planeRepo.delete(plane);
                else
                    throw new Exception();
                return new ResponseEntity<String>("succes", HttpStatus.OK);
            } else {
                System.out.println("Nisi admin bato");
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/searchFlights")
    public ResponseEntity<String> searchFlights(@RequestBody FlightForm form) {
        try {
            Plane plane = planeRepo.findByName(form.getPlaneName());
            Flight flights = flightRepo.findFlight(plane, form.getStartPlace(), form.getEndPlace());
            return new ResponseEntity<String>(flights.toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/page/{x}")
//    public ResponseEntity<String> pagination(@PathVariable int x) {
//        try {
//            List<Flight> flights = flightRepo.findFlightsByPage((x - 1) * 5, x * 5);
//            String fl = toStringFlights(flights);
//            return new ResponseEntity<String>(fl, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<String>(HttpStatus.BAD_GATEWAY);
//        }
//    }

    @PostMapping("/testPayment")
    public ResponseEntity<String> tptp(@RequestHeader(value = "Authorization") String token, @RequestBody FlightForm form) {
        try {
            Plane plane = planeRepo.findByName(form.getPlaneName());
            Flight flights = flightRepo.findFlight(plane, form.getStartPlace(), form.getEndPlace());
            System.out.println(flights.getId() + " " + flights.getEndPlace());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            FlightFormPlane ffp = new FlightFormPlane(flights.getId(), flights.getPlane().getCapacity(), flights.getFlightDuration());
            httpHeaders.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Authorization", token);
            HttpEntity<FlightFormPlane> httpEntity = new HttpEntity<>(ffp, httpHeaders);
            ResponseEntity<String> answer = restTemplate.exchange("http://localhost:8082/payment", HttpMethod.POST, httpEntity, String.class);

            return new ResponseEntity<String>(answer.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_GATEWAY);
        }
    }
}

