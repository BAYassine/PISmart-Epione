package resources;

import java.util.Date;
import java.util.TimerTask;

public class AutomaticEmailSender extends TimerTask {
	  public void run() {
		  Email em=new Email();
		  em.sendEmail();
	        System.out.println("The task is called " + new Date());
	       
	    }

}
