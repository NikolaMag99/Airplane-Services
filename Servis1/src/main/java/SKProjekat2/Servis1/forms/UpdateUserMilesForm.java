package SKProjekat2.Servis1.forms;

public class UpdateUserMilesForm {
    private int miles;
    private String token;

    public UpdateUserMilesForm(int miles, String token) {
        super();
        this.miles = miles;
        this.token = token;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
