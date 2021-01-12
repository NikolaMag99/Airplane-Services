package SKProjekat2.Servis1.forms;

public class RegistrationForm {

    private String name;
    private String surName;
    private String email;
    private String password;
    private int passportNumber;

    public RegistrationForm(String name, String surName, String email, String password, int passportNumber) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

}
