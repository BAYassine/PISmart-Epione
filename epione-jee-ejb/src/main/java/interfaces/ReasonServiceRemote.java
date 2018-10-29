package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Reason;


@Remote
public interface ReasonServiceRemote {
	public int addReason(Reason r,int idSpeciality);
	public int updateReason(Reason r,int idSpeciality );
	public void removeReason(int idR);
	public List<Reason> searchReasonBySpeciality(int idSpeciality);

}
