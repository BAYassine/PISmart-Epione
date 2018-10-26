package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

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
	private int id;
	private String username;
	private String password;
	private String email;
	private Date registred_at;
	@Enumerated(EnumType.STRING)
	private Roles role;

	public User(){}

	public User(String username, String password, String email, Date registred_at, Roles role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.registred_at = new Date();
		this.role = role;
	}

	@OneToOne
	private Profile profile;
	@XmlAttribute
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
		return registred_at;
	}
	public void setRegistred_at(Date registred_at) {
		this.registred_at = registred_at;
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

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", registred_at=" + registred_at +
				", role=" + role.toString() +
				", profile=" + profile;
	}
}
