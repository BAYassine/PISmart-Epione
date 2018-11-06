package entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Doctor extends User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String presentation;
    private String name;
    private String image;
    private double latitude;
    private double longitude;
    private String address;
    private String city;
    private String nbreRPPS;
    private String statuts;
    private String nbreInscriptionOrdre;
    private String nbreRCS;
    private String memberAGA;
    private String formeJuridique;
    private String adresseSocialSiege;
    private String socialReason;
    @ElementCollection
    @CollectionTable(name ="skills")
    @JsonIgnore
    private Set<String> skills = new HashSet<String>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Availability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Path> paths = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    private Speciality speciality;

    /*Fares*/
    @ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Reason> reasons=new HashSet<>();

    public Doctor() {
        super();
    }

    public Doctor(Speciality speciality) {
        this.speciality = speciality;
    }

    public Doctor(User u) {
        this.username = u.username;
        this.password = u.password;
        this.email = u.email;
        this.role = u.role;
        this.registered_at = new Date();
        this.last_login = new Date();
        this.confirmed = u.confirmed;
    }

    @XmlTransient
    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }



    public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNbreRPPS() {
		return nbreRPPS;
	}

	public void setNbreRPPS(String nbreRPPS) {
		this.nbreRPPS = nbreRPPS;
	}

	public String getStatuts() {
		return statuts;
	}

	public void setStatuts(String statuts) {
		this.statuts = statuts;
	}

	public String getNbreInscriptionOrdre() {
		return nbreInscriptionOrdre;
	}

	public void setNbreInscriptionOrdre(String nbreInscriptionOrdre) {
		this.nbreInscriptionOrdre = nbreInscriptionOrdre;
	}

	public String getNbreRCS() {
		return nbreRCS;
	}

	public void setNbreRCS(String nbreRCS) {
		this.nbreRCS = nbreRCS;
	}

	public String getMemberAGA() {
		return memberAGA;
	}

	public void setMemberAGA(String memberAGA) {
		this.memberAGA = memberAGA;
	}

	public String getFormeJuridique() {
		return formeJuridique;
	}

	public void setFormeJuridique(String formeJuridique) {
		this.formeJuridique = formeJuridique;
	}

	public String getAdresseSocialSiege() {
		return adresseSocialSiege;
	}

	public void setAdresseSocialSiege(String adresseSocialSiege) {
		this.adresseSocialSiege = adresseSocialSiege;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public Set<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(Set<Reason> reasons) {
		this.reasons = reasons;
	}

}
