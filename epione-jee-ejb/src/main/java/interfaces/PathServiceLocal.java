package interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Doctor;
import entities.Path;
import entities.Patient;
import entities.Treatment;

@Local
public interface PathServiceLocal {
	public int addPath(Path path);
	public int updatePath(Path path);
	public void deletePath(Path path);
	public List<Path> getAllPaths();
	public Path getPathById(int id);
	public List<Path> getPathsByDesc(String desc) ;
	public List<Path> getPathsByDate(Date date) ;
    public List<Path> getPathsDateGreaterThen(Date date);
    public List<Path> getPathsDateLessThen(Date date);
    public List<Path> getPathsByDate(Date date, String desc);
	public Doctor getPathDoctor(int id);
	public Patient getPathPatient(int id);
	public Path addTreatToPath(int id ,Treatment treat);
	List<Path> getPatientsPath(int id);
	List<Path> getDoctorsPath(int id);


}
