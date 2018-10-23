package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;

@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
