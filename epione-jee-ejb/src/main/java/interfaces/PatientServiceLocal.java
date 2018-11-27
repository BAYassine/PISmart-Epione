package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Patient;

@Local
public interface PatientServiceLocal {

    Patient findPatient(String username);
    void update(Patient patient);

    int create(Patient u);
    public List<Patient> findAll();
}
