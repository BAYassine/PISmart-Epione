package resources;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
public class Email {
	public Email(){}
	public static void sendEmail(){
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
			InternetAddress.parse("oumayma.habbouri@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Email jee every one minute");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	   public static void main(String[] args) {
	        Timer timer = new Timer();
	        Calendar date = Calendar.getInstance();
	        //Setting the date from when you want to activate scheduling
	        
	        date.set(2011, 3, 28, 21, 28);
	        //execute every 3 seconds
	        timer.schedule(new AutomaticEmailSender(), new Date(), 60*1000);
	    }
}