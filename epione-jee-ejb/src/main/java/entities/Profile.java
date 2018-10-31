package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity implementation class for Entity: Profile
 *
 */
@Entity

public class Profile implements Serializable {

    public enum Gender {FEMALE, MALE}

    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private Date birthDate;

    @NotNull
    private Gender gender;

    @NotNull
    private String address;

    @Pattern(regexp = "[1-9][0-9]{7}")
    private String telephone;

    @OneToOne(mappedBy="profile")
    @JsonIgnore
	private User user;
	
	

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void copy(Profile p) {
        this.id = p.id;
        if (this.firstname == null)
            this.firstname = p.firstname;
        if(this.lastname == null)
            this.lastname = p.lastname;
        if(this.birthDate== null)
            this.birthDate= p.birthDate;
        if(this.gender== null)
            this.gender= p.gender;
        if(this.address== null)
            this.address= p.address;
        if(this.telephone== null)
            this.telephone= p.telephone;
    }

}
