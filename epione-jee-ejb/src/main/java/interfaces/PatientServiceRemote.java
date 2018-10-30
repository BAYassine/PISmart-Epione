package interfaces;

import entities.Patient;

import javax.ejb.Remote;

@Remote
public interface PatientServiceRemote {
    
    Patient findPatient(String username);
    void update(Patient patient);

}
