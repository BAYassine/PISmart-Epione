package interfaces;

import entities.Patient;

import javax.ejb.Local;

@Local
public interface PatientServiceLocal {

    Patient findPatient(String username);
    void update(Patient patient);

}
