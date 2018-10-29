package interfaces;

import javax.ejb.Remote;

import entities.Rating;


@Remote
public interface RateServiceRemote {
	
	public int AddRate(Rating r);
	public int EditRate(Rating r,int rate, String comment);

}
