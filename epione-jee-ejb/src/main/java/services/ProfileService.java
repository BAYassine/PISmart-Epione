package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Appointment;
import entities.Profile;
import entities.User;
import interfaces.ProfileServiceLocal;
import interfaces.ProfileServiceRemote;

@Stateless
public class ProfileService implements ProfileServiceLocal, ProfileServiceRemote {


}
