package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.MessageServiceLocal;
import interfaces.MessageServiceRemote;

@Stateless
public class MessageService implements MessageServiceLocal, MessageServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
