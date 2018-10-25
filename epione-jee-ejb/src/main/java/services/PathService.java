package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Path;
import interfaces.PathServiceLocal;
import interfaces.PathServiceRemote;

@Stateless
public class PathService implements PathServiceLocal, PathServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int addPath(Path path) {
		em.persist(path);
		return path.getId();
	}

	@Override
	public int updatePath(Path path) {
		Path t	= em.find(Path.class, path);
		t.setDescription(path.getDescription());
		t.setDate_path(path.getDate_path());
		t.setList_treat(path.getList_treat());
		return path.getId();
	}

	@Override
	public void deletePath(Path path) {
		em.remove(path);
	}

	@Override
	public List<Path> getAllPaths() {
		System.out.println("test");
		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p", Path.class);
		 System.out.println("SELECT p FROM Path p");
		 return (List<Path>) query.getResultList();
	}

	@Override
	public Path getPathById(int id) {
		System.out.println("test");
		return em.find(Path.class, id);
	}
}
