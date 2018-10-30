package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@XmlRootElement
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {
	
	public enum Roles { ROLE_ADMIN, ROLE_DOCTOR, ROLE_PATIENT}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @NotNull
    @Column(unique = true)
    protected String username;
    protected String password;
    @NotNull
    @Pattern(regexp = "[a-z][a-zA-Z0-9]*@[a-z0-9]*\\.[a-z0-9]*", message = "Email is not valid")
    protected String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+01")
    protected Date registered_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+01")
    protected Date last_login;
    @Enumerated(EnumType.STRING)
    protected Roles role;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	protected Profile profile;

	public User(){
		this.registered_at = new Date();
	}

	public User(String username, String password, String email, Date registered_at, Roles role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.registered_at = new Date();
		this.role = role;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegistred_at() {
		return registered_at;
	}
	public void setRegistred_at(Date registred_at) {
		this.registered_at = registred_at;
	}
	public String getRole() {
		return role.toString();
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public void copy(User u){
		this.username = u.username;
		this.password = u.password;
		this.email = u.email;
		this.role = u.role;
		this.registered_at = u.registered_at;
		this.last_login= u.last_login;
		if(this.profile == null && u.profile != null)
			this.profile = u.profile;
		else if (this.profile != null && u.profile != null)
			this.profile.copy(u.profile);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", registred_at=" + registered_at +
				", role=" + role.toString() +
				", profile=" + profile;
	}
}
