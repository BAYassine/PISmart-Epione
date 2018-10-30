package entities;

import java.util.List;

public class DoctolibDoctor extends Doctolib{
	
	private String nbreRPPS;
	private String statuts;
	private String nbreInscriptionOrdre;
	private String nbreRCS;
	private Boolean memberAGA;
	private String formeJuridique;
	private String adresseSocialSiege;
	private String socialReason;
	private double lat;
	private double lng;
	private List<String> skills;
	
	@Override
	public String toString() {
		return "DoctolibDoctor [nbreRPPS=" + nbreRPPS + ", statuts=" + statuts + ", nbreInscriptionOrdre="
				+ nbreInscriptionOrdre + ", nbreRCS=" + nbreRCS + ", memberAGA=" + memberAGA + ", formeJuridique="
				+ formeJuridique + ", adresseSocialSiege=" + adresseSocialSiege + ", socialReason=" + socialReason
				+ "]";
	}
	
	
	
	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
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
	public Boolean getMemberAGA() {
		return memberAGA;
	}
	public void setMemberAGA(Boolean memberAGA) {
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
	
	
	

}
