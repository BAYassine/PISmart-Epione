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

import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
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
			
			LanguageTranslator languageTranslator = new LanguageTranslator(
				    "2018-05-01",
				    "a8c4aef8-5697-45f1-becf-740cf6a19be2",
				    "rnPCZt8D87im");

			languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
			
			Doctolib doctors = new Doctolib();
			doctors.setAddress(headline.parent().select(".dl-text").text());
			doctors.setCity(headline.select("h3").select("a").attr("href").substring(headline.select("h3").select("a").attr("href").indexOf("/",2) + 1, headline.select("h3").select("a").attr("href").indexOf("/",headline.select("h3").select("a").attr("href").indexOf("/",2) + 1)));
			doctors.setName(headline.select("h3").select("div").html());
			doctors.setImg("http:"+headline.select("img").attr("src"));
			doctors.setPath(headline.select("h3").select("a").attr("href"));
			TranslateOptions translateOptionsSpe = new TranslateOptions.Builder()
					  .addText(headline.select(".dl-search-result-subtitle").text())
					  .modelId("fr-en")
					  .build();

			TranslationResult spe = languageTranslator.translate(translateOptionsSpe).execute();
			doctors.setSpeciality(spe.getTranslations().get(0).getTranslationOutput());
			lst.add(doctors);
		}
		
		return lst;
	}

	@Override
	public DoctolibDoctor getDoctorByPath(String path) throws IOException {
		
		LanguageTranslator languageTranslator = new LanguageTranslator(
			    "2018-05-01",
			    "a8c4aef8-5697-45f1-becf-740cf6a19be2",
			    "rnPCZt8D87im");

		languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
		
		DoctolibDoctor doctor = new DoctolibDoctor();
		
		String url = "https://www.doctolib.fr"+path;
		Document doc;
		Connection cnx;
		
		cnx = Jsoup.connect(url);
		doc = cnx.get();
		TranslateOptions translateOptionsDesc = new TranslateOptions.Builder()
				  .addText(doc.selectFirst(".dl-profile-bio").text())
				  .modelId("fr-en")
				  .build();

		TranslationResult description = languageTranslator.translate(translateOptionsDesc).execute();
		doctor.setDescription(description.getTranslations().get(0).getTranslationOutput());
		doctor.setName(doc.selectFirst(".dl-profile-header-name").text());
		doctor.setImg("https:"+doc.selectFirst(".dl-profile-header-photo").selectFirst("img").attr("src"));
		doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-practice-name").remove();
		doctor.setAddress(doc.selectFirst(".dl-profile-doctor-place-map").parent().selectFirst(".dl-profile-card-content").selectFirst(".dl-profile-text").text());
		doctor.setPath(path);
		doctor.setLat(Double.valueOf(doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lat\":")+6,doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf(",",doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lat\":")+6))));
		doctor.setLng(Double.valueOf(doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lng\":")+6,doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("}",doc.selectFirst(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lng\":")+6))));
		doctor.setCity(path.substring(path.indexOf("/",2) + 1, path.indexOf("/",path.indexOf("/",2) + 1)));
		TranslateOptions translateOptionsSpe = new TranslateOptions.Builder()
				  .addText(doc.selectFirst(".dl-profile-header-speciality").text())
				  .modelId("fr-en")
				  .build();

		TranslationResult speciality = languageTranslator.translate(translateOptionsSpe).execute();
		doctor.setSpeciality(speciality.getTranslations().get(0).getTranslationOutput());
		Elements skills = doc.select(".dl-profile-skill-chip");
		List<String> doctorSkills = new ArrayList<String>();
		for (Element skill : skills) {
			TranslateOptions translateOptions = new TranslateOptions.Builder()
					  .addText(skill.text())
					  .modelId("fr-en")
					  .build();

			TranslationResult result = languageTranslator.translate(translateOptions).execute();
			doctorSkills.add(result.getTranslations().get(0).getTranslationOutput());
		}
		doctor.setSkills(doctorSkills);
		Elements newsHeadlines = doc.selectFirst(".js-profile-legals-details-bloc").select(".dl-profile-row-section");
		for (Element headline : newsHeadlines) {
			switch (headline.selectFirst(".dl-profile-card-subtitle").text()) {
			case "Statut":
				headline.selectFirst(".dl-profile-card-subtitle").remove();
				TranslateOptions translateOptions = new TranslateOptions.Builder()
						  .addText(headline.text())
						  .modelId("fr-en")
						  .build();

				TranslationResult result = languageTranslator.translate(translateOptions).execute();
				doctor.setStatuts(result.getTranslations().get(0).getTranslationOutput());
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
				TranslateOptions translateOptionsJuridique = new TranslateOptions.Builder()
						  .addText(headline.text())
						  .modelId("fr-en")
						  .build();

				TranslationResult juridique = languageTranslator.translate(translateOptionsJuridique).execute();
				doctor.setFormeJuridique(juridique.getTranslations().get(0).getTranslationOutput());
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
		LanguageTranslator languageTranslator = new LanguageTranslator(
			    "2018-05-01",
			    "a8c4aef8-5697-45f1-becf-740cf6a19be2",
			    "rnPCZt8D87im");

		languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
		
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
		other.setCity(path.substring(path.indexOf("/",2) + 1, path.indexOf("/",path.indexOf("/",2) + 1)));
		TranslateOptions translateOptionsSpe = new TranslateOptions.Builder()
				  .addText(doc.selectFirst(".dl-profile-header-speciality").text())
				  .modelId("fr-en")
				  .build();

		TranslationResult speciality = languageTranslator.translate(translateOptionsSpe).execute();
		other.setSpeciality(speciality.getTranslations().get(0).getTranslationOutput());
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
					doctor.setCity(other.getCity());
					TranslateOptions translateOptionsSpeciality = new TranslateOptions.Builder()
							  .addText(contentChildren.get(contentChildren.indexOf(contentChild) - 1).text())
							  .modelId("fr-en")
							  .build();

					TranslationResult spe = languageTranslator.translate(translateOptionsSpeciality).execute();
					doctor.setSpeciality(spe.getTranslations().get(0).getTranslationOutput());
					lst.add(doctor);
				}
	        }
		}
		other.setLstDoctors(lst);
		return other;
	}

	@Override
	public List<Doctolib> getListDoctorsByNameAndLocation(String name, String location, String page) throws IOException {
		List<Doctolib> lst = new ArrayList<Doctolib>();
		
		if(location.isEmpty()){
			location = "france";
		}
		if(page.isEmpty()){
			page = "0";
		}
		
		String url = "https://www.doctolib.fr/doctors/"+name+"/"+location;
		Document doc;
		Connection cnx;
		
		
		if(Integer.parseInt(page)>1){
			url += "?page="+page;
		}
		
		cnx = Jsoup.connect(url);
		doc = cnx.get();
		Elements newsHeadlines = doc.select(".dl-search-result-title-container");
		for (Element headline : newsHeadlines) {
			
			LanguageTranslator languageTranslator = new LanguageTranslator(
				    "2018-05-01",
				    "a8c4aef8-5697-45f1-becf-740cf6a19be2",
				    "rnPCZt8D87im");

			languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
			
			Doctolib doctors = new Doctolib();
			doctors.setAddress(headline.parent().select(".dl-text").text());
			doctors.setCity(headline.select("h3").select("a").attr("href").substring(headline.select("h3").select("a").attr("href").indexOf("/",2) + 1, headline.select("h3").select("a").attr("href").indexOf("/",headline.select("h3").select("a").attr("href").indexOf("/",2) + 1)));
			doctors.setName(headline.select("h3").select("div").html());
			doctors.setImg("http:"+headline.select("img").attr("src"));
			doctors.setPath(headline.select("h3").select("a").attr("href"));
			TranslateOptions translateOptionsSpe = new TranslateOptions.Builder()
					  .addText(headline.select(".dl-search-result-subtitle").text())
					  .modelId("fr-en")
					  .build();

			TranslationResult spe = languageTranslator.translate(translateOptionsSpe).execute();
			doctors.setSpeciality(spe.getTranslations().get(0).getTranslationOutput());
			lst.add(doctors);
		}
		
		return lst;
	}

}
