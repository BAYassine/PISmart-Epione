package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Path;

@Local
public interface PathServiceLocal {
	public int addPath(Path path);
	public int updatePath(Path path);
	public void deletePath(Path path);
	public List<Path> getAllPaths();
	public Path getPathById(int id);
	

}
