package entities;

import java.util.ArrayList;
import java.util.List;

public class DoctolibOther extends Doctolib{
	
	private List<Doctolib> lstDoctors = new ArrayList<Doctolib>();

	public List<Doctolib> getLstDoctors() {
		return lstDoctors;
	}

	public void setLstDoctors(List<Doctolib> lstDoctors) {
		this.lstDoctors = lstDoctors;
	} 
	
	
	
}
