package mail;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {
	
	private static String HOST = "smtp.gmail.com";
	private static String PORT = "587";
	private static String TO = "to mail";
	private static String FROM = "from mail";
	private static String FROM_PASSWORD = "from password";

	public Session getSession() {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(properties, null);
		return session;
	}
	
	public MimeMessage getMessage(Session session, String subject, String content) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		message.setSubject(subject);
		message.setContent(content, "text/html");
		return message;
	}
	
	public void sendMail(String subject, String content) throws MessagingException {
		Session session = this.getSession();
		MimeMessage message = this.getMessage(session, subject, content);
		Transport transport = session.getTransport("smtp");
		transport.connect(HOST, FROM, FROM_PASSWORD);
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("Email sent successfully!");
	}

	public static void main(String[] args) {
		String subject = "your subject";
		String content = "your content";
		try {
			Mail mail = new Mail();
			mail.sendMail(subject, content);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}