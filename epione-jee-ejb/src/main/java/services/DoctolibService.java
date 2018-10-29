package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import entities.Doctolib;
import entities.DoctolibDoctor;
import entities.DoctolibOther;
import interfaces.DoctolibServiceLocal;
import interfaces.DoctolibServiceRemote;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.DOMConfiguration;

import com.sun.mail.handlers.text_html;

@Stateless
public class DoctolibService implements DoctolibServiceLocal, DoctolibServiceRemote{

	@Override
	public List<Doctolib> getListDoctorsBySpecialityAndLocation(String speciality, String location, String page) throws IOException {
		List<Doctolib> lst = new ArrayList<Doctolib>();
		
		if(location.isEmpty()){
			location = "france";
		}
		if(page.isEmpty()){
			page = "0";
		}
		
		String url = "https://www.doctolib.fr/"+speciality+"/"+location;
		Document doc;
		Connection cnx;
		
		
		if(Integer.parseInt(page)>1){
			url += "?page="+page;
		}
		
		cnx = Jsoup.connect(url);
		doc = cnx.get();
		Elements newsHeadlines = doc.select(".dl-search-result-title-container");
		for (Element headline : newsHeadlines) {
			lst.add(new Doctolib(headline.select("h3").select("div").html(), "http:"+headline.select("img").attr("src"), headline.parent().select(".dl-text").text(),headline.select("h3").select("a").attr("href"),headline.select(".dl-search-result-subtitle").text()));
		}
		
		return lst;
	}

	@Override
	public DoctolibDoctor getDoctorByPath(String path) throws IOException {
		
		DoctolibDoctor doctor = new DoctolibDoctor();
		
		String url = "https://www.doctolib.fr"+path;
		Document doc;
		Connection cnx;
		
		cnx = Jsoup.connect(url);
		doc = cnx.get();
		doctor.setName(doc.selectFirst(".dl-profile-header-name").text());
		doctor.setImg("https:"+doc.selectFirst(".dl-profile-header-photo").selectFirst("img").attr("src"));
		doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-practice-name").remove();
		doctor.setAddress(doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-text").text());
		doctor.setPath(path);
		doctor.setSpeciality(doc.selectFirst(".dl-profile-header-speciality").text());
		Elements newsHeadlines = doc.selectFirst(".js-profile-legals-details-bloc").select(".dl-profile-row-section");
		for (Element headline : newsHeadlines) {
			switch (headline.selectFirst(".dl-profile-card-subtitle").text()) {
			case "Statut":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setStatuts(headline.text());
				break;
			case "Numéro RPPS":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setNbreRPPS(headline.text());
				break;
			case "Numéro d'inscription à l'ordre":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setNbreInscriptionOrdre(headline.text());
				break;
			case "Raison sociale":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setSocialReason(headline.text());
				break;
			case "Numéro RCS":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setNbreRCS(headline.text());
				break;
			case "Adresse du siège social":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setAdresseSocialSiege(headline.text());
				break;
			case "Forme juridique":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				doctor.setFormeJuridique(headline.text());
				break;
			case "Membre d'une AGA":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				if(headline.text().contains("Non"))
					doctor.setMemberAGA(false);
				else
					doctor.setMemberAGA(true);
				break;
			default:
				break;
			}
		}
		
		return doctor;
	}

	@Override
	public DoctolibOther getOtherByPath(String path) throws IOException {
		DoctolibOther other = new DoctolibOther();
		
		String url = "https://www.doctolib.fr"+path;
		Document doc;
		Connection cnx;
		
		cnx = Jsoup.connect(url);
		doc = cnx.get();
		other.setName(doc.selectFirst(".dl-profile-header-name").text());
		other.setImg("https:"+doc.selectFirst(".dl-profile-header-photo").selectFirst("img").attr("src"));
		doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-practice-name").remove();
		other.setAddress(doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-text").text());
		other.setPath(path);
		other.setSpeciality(doc.selectFirst(".dl-profile-header-speciality").text());
		List<Doctolib> lst = new ArrayList<Doctolib>();
		Element content = doc.selectFirst(".dl-profile-team-profiles").parent();
		Elements contentChildren = content.select("*");
		for (Element contentChild : contentChildren) {
			if (contentChild.hasClass("dl-profile-team-profiles")) {
				Elements newsHeadlines = contentChild.select(".dl-profile-team-profile");
				for (Element headline : newsHeadlines) {
					Doctolib doctor = new Doctolib();
					doctor.setAddress(other.getAddress());
					doctor.setImg("https:"+headline.selectFirst("img").attr("src"));
					doctor.setName(headline.text());
					doctor.setPath(headline.selectFirst("a").attr("href"));
					doctor.setSpeciality(contentChildren.get(contentChildren.indexOf(contentChild) - 1).text());
					lst.add(doctor);
				}
	        }
		}
		other.setLstDoctors(lst);
		return other;
	}

}
