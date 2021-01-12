package SKProjekat2.Servis2.forms;

public class FlightForm {
    private String planeName;
    private String startPlace;
    private String endPlace;
    private int flightDuration;
    private int price;

    public FlightForm(String planeName, String startPlace, String endPlace, int flightDuration, int price) {
        this.planeName = planeName;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.flightDuration = flightDuration;
        this.price = price;
    }


    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
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


}
