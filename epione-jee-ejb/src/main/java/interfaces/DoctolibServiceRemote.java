package interfaces;

import java.io.IOException;
import java.util.List;

import javax.ejb.Remote;

import entities.Doctolib;
import entities.DoctolibDoctor;
import entities.DoctolibOther;

@Remote
public interface DoctolibServiceRemote {
	
	public List<Doctolib> getListDoctorsBySpecialityAndLocation(String speciality, String location, String page) throws IOException;
	public DoctolibDoctor getDoctorByPath(String path) throws IOException;
	public DoctolibOther getOtherByPath(String path) throws IOException;
}
