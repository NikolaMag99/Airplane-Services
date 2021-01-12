package SKProjekat2.Servis1.forms;

public class FlightForm {
    private String planeName;
    private String startPlace;
    private String endPlace;
    private int flightDuration;

    public FlightForm(String planeName, String startPlace, String endPlace, int flightDuration) {
        super();
        this.planeName = planeName;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.flightDuration = flightDuration;
    }

    public FlightForm() {

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


}
