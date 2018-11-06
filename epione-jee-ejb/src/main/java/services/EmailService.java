package services;

import java.text.ParseException;
import java.util.*;

import javax.ejb.EJB;
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.print.attribute.standard.DateTimeAtCompleted;

import entities.Appointment;
import interfaces.AppointmentServiceLocal;
import services.AutomaticEmailSender;
public class EmailService {
	@EJB
	private AppointmentServiceLocal appServ;
	public EmailService(){
		
	}
	public  void sendEmail(String subject, String content,String email){
		final String username = "oumayma.habouri@gmail.com";
		final String password = "14761952";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("oumayma.habouri@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Appointment> checkTomorrowAppointments() throws ParseException{
		 List<Appointment> listApp=new ArrayList<>();
		 Date dt = new Date();
		 Calendar c = Calendar.getInstance(); 
		 c.setTime(dt); 
		 c.add(Calendar.DATE, 1);
		 dt = c.getTime();
		 listApp=appServ.getAppointmentByDate(dt.toString());
		return listApp;
			 
	}
	   public static void main(String[] args) throws ParseException {
		   EmailService em=new EmailService();
	        Timer timer = new Timer();
	     List<Appointment> listApp=new ArrayList<>();
	     listApp=em.checkTomorrowAppointments();
	     if(!listApp.isEmpty()){
	    	 em.sendEmail("Appointment reminder","You have an appointment tomororw.","oumayma.habbouri@gmail.com");
	     }
	        timer.schedule(new AutomaticEmailSender(), new Date(), 60*1000);
	    }
}