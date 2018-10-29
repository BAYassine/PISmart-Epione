package services;

import java.util.Date;
import java.util.TimerTask;

public class AutomaticEmailSender extends TimerTask {
	  public void run() {
	        System.out.println("The task is called " + new Date());
	       
	    }

}
