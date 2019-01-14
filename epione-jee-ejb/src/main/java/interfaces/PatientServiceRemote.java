package interfaces;

import entities.Patient;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PatientServiceRemote {
    
    Patient findPatient(String username);
    void update(Patient patient);
    public List<Patient> findAll();

}
