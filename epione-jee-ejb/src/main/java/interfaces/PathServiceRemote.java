package interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import entities.Doctor;
import entities.Path;

@Remote
public interface PathServiceRemote {
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
}
