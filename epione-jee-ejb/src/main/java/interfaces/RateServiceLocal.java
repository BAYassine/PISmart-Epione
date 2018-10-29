package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Rating;


@Local
public interface RateServiceLocal {
	
	public int AddRate(Rating r);
	public int EditRate(Rating r,int rate, String comment);
	public List<Rating> getAllRate();

}
