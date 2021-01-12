package SKProjekat2.Servis2.entity;

import javax.persistence.*;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Plane plane;
    private String startPlace;
    private String endPlace;
    private int flightDuration;
    private int price;

    public Flight(Plane plane, String startPlace, String endPlace, int flightDuration, int price) {
        super();
        this.plane = plane;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.flightDuration = flightDuration;
        this.price = price;
    }

    public Flight() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " " + plane.getName() + " " + startPlace + " " + endPlace + " " + String.valueOf(flightDuration);
    }
}
