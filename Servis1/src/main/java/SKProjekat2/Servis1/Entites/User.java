package SKProjekat2.Servis1.Entites;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surName;
    private String email;
    private String password;
    private int passportNumber;
    private int miles;

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    @OneToMany
    List<CreditCard> list;


    public User() {

    }

    public User(String name, String lastName, String email, String password, int passportNumber) {
        this.name = name;
        this.surName = lastName;
        this.email = email;
        this.password = password;
        this.passportNumber = passportNumber;
        this.list = new ArrayList<CreditCard>();
        this.miles = 0;
    }

    public List<CreditCard> getList() {
        return list;
    }

    public void setList(List<CreditCard> list) {
        this.list = list;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", surName=" + surName + ", email=" + email + ", password="
                + password + ", passportNumber=" + passportNumber + ", miles=" + miles + ", list=" + list + "]";
    }


}
