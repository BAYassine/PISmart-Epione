package resources;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
public class Email {
	public Email(){}
	public  void sendEmail(){
		final String username = "oumayma.habouri@gmail.com";
		final String password = "";
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
			message.setText("Email jee");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}