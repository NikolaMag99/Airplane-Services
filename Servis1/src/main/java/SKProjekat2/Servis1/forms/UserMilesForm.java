package SKProjekat2.Servis1.forms;

public class UserMilesForm {
    private long userId;
    private int miles;

    @Override
    public String toString() {
        return "User miles: UserId=" + userId + ", Miles=" + miles;
    }

    public UserMilesForm(long userId, int miles) {
        this.userId = userId;
        this.miles = miles;
    }

    public UserMilesForm() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

}
