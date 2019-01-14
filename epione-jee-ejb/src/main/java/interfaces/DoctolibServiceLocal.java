package interfaces;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import entities.Doctolib;
import entities.DoctolibDoctor;
import entities.DoctolibOther;

@Local
public interface DoctolibServiceLocal {
	
	public List<Doctolib> getListDoctorsBySpecialityAndLocation(String speciality, String location, String page) throws IOException;
	public List<Doctolib> getListDoctorsByNameAndLocation(String name, String location, String page) throws IOException;
	public DoctolibDoctor getDoctorByPath(String path) throws IOException;
	public DoctolibOther getOtherByPath(String path) throws IOException;
}
