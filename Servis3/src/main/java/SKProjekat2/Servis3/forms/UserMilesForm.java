package SKProjekat2.Servis3.forms;

public class UserMilesForm {
	private long userId;
	private int miles;
	public UserMilesForm(long id, int miles) {
		this.userId = id;
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
	@Override
	public String toString() {
		return "UserMiles id= " + userId + ", miles= " + miles;
	}
	
	
	
}
