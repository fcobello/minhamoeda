package br.com.cobello.minhamoeda.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	private static String USER_NAME = ""; // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = ""; // GMail password
	private static String TEMPLATE = "<html><header><img src=\"http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-11/72/cash-icon.png\" alt=\"Minha Moeda\"></header><body><h1>#TEXT</h1><br><br><img src=\"https://2.bp.blogspot.com/-DyMbSHnLFiM/WUxm9XrrV-I/AAAAAAAABxk/8lj46dCmKlMgVS1i9Qkq6LqxG9eUL9MBACLcBGAs/s1600/Banner%2B1.jpg\" alt=\"Merchan\"></body></html>";

	public static void sendFromGMail(String[] to, String subject, String body) {
		Properties props = System.getProperties();

		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", USER_NAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(USER_NAME));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(TEMPLATE.replace("#TEXT", body), "utf-8", "html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", USER_NAME, PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

}
