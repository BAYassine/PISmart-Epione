package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Doctor;
import entities.Path;
import entities.Patient;
import entities.Treatment;
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
		Path t	= em.find(Path.class, path.getId());
		t.setDescription(path.getDescription());
		t.setDate_path(path.getDate_path());
		t.setList_treat(path.getList_treat());
		return path.getId();
	}

	@Override
	public void deletePath(Path path) {
		Path t = em.find(Path.class, path.getId());
		em.remove(t);
	}


	@Override
	public List<Path> getAllPaths() {
		 System.out.println("req num 1) SELECT p FROM Path p");

		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p", Path.class);
		 return (List<Path>) query.getResultList();
	}
	@Override
	public List<Path> getPathsByDesc(String desc) {
		 System.out.println("req num 2) SELECT p FROM Path p where p.description like :desc");
		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p where p.description like :desc", Path.class);
		 return (List<Path>) query.setParameter("desc", "%"+desc+"%").getResultList();
	}
	@Override
	public List<Path> getPathsByDate(Date date) {
		 System.out.println("req num 3) SELECT p FROM Path p where p.date_path = :date");

		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p where p.date_path = :date", Path.class);
		 return (List<Path>) query.setParameter("date", date).getResultList();
	}
	@Override
	public List<Path> getPathsDateGreaterThen(Date date) {
		System.out.println("req num 4) SELECT p FROM Path p where p.date_path > :date");
		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p where p.date_path > :date", Path.class);
		 return (List<Path>) query.setParameter("date", date).getResultList();

	}
	@Override
	public List<Path> getPathsDateLessThen(Date date) {
		System.out.println("req num 5) SELECT p FROM Path p where p.date_path < :date");
		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p where p.date_path < :date", Path.class);
		 return (List<Path>) query.setParameter("date", date).getResultList();

	}
	
	@Override
	public List<Path> getPathsByDate(Date date, String desc) {
		 System.out.println("req num 6) SELECT p FROM Path p where p.date_path = :date and p.description like :desc");

		 TypedQuery<Path> query = em.createQuery("SELECT p FROM Path p where p.date_path = :date", Path.class);
		 return (List<Path>) query.setParameter("date", date).setParameter("desc", "%"+desc+"%").getResultList();
	}

	@Override
	public Path getPathById(int id) {
		System.out.println("test");
		Path t = em.find(Path.class, id);
		System.out.println("*****************test");
		t.getList_treat().iterator();
		return t;
	}
	
	
	@Override
	public Doctor getPathDoctor(int id) {
			System.out.println("req num 7) SELECT p.doctor FROM Path p WHERE p.id = :id");
		 TypedQuery<Doctor> query = em.createQuery("SELECT p.doctor FROM Path p WHERE p.id = :id", Doctor.class);
		 return (Doctor) query.setParameter("id", id).getSingleResult();

	}
	@Override
	public Patient getPathPatient(int id) {
			System.out.println("req num 8) SELECT p.patient FROM Path p WHERE p.id = :id");
		 TypedQuery<Patient> query = em.createQuery("SELECT p.patient FROM Path p WHERE p.id = :id", Patient.class);
		 return (Patient) query.setParameter("id", id).getSingleResult();

	}
	
	@Override
	public Path addTreatToPath(int id ,Treatment treat) {
		Path path = em.find(Path.class, id);
		List<Treatment> treats = path.getList_treat();
		treats.add(treat);
		path.setList_treat(treats);
		return path;
	}
	
	
}
