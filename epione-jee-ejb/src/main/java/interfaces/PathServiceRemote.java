package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Path;

@Remote
public interface PathServiceRemote {
	public int addPath(Path path);
	public int updatePath(Path path);
	public void deletePath(Path path);
	public List<Path> getAllPaths();
	public Path getPathById(int id);

}
