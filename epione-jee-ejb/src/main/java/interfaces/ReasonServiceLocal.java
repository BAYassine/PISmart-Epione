package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Reason;


@Local
public interface ReasonServiceLocal {
	public int addReason(Reason r,int idSpeciality);
	public int updateReason(Reason r,int idSpeciality);
	public void removeReason(int idR);
	public List<Reason> searchReasonBySpeciality(int idSpeciality);

}
