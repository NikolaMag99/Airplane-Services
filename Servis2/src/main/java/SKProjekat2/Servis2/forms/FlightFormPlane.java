package SKProjekat2.Servis2.forms;


public class FlightFormPlane {
    private long flightId;
    private int capacity;
    private int duration;

    public FlightFormPlane(long flightId, int capacity, int duration) {
        super();
        this.flightId = flightId;
        this.capacity = capacity;
        this.duration = duration;
    }

    public FlightFormPlane() {
        super();
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
