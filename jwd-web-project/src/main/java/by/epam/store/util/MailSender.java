package by.epam.store.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The utility is responsible for sending messages
 * 
 * @author Mariya Kuksar
 */
public class MailSender {
	private static final Logger logger = LogManager.getLogger();
	private static final String MAIL_PROPERTIES = "mail.properties";
	private static final String MAIL_USER_NAME = "mail.user.name";
	private static final String MAIL_USER_PASSWORD = "mail.user.password";
	private static final Properties properties = new Properties();
	private static final Authenticator authenticator;

	static {
		ClassLoader classLoader = MailSender.class.getClassLoader();
		try (InputStream resourceAsStream = classLoader.getResourceAsStream(MAIL_PROPERTIES)) {
			properties.load(resourceAsStream);
			authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty(MAIL_USER_NAME),
						properties.getProperty(MAIL_USER_PASSWORD));
			}
		};
		} catch (IOException e) {
			logger.error("Error uploading" + MAIL_PROPERTIES, e);
			throw new RuntimeException("Error uploading a file" + MAIL_PROPERTIES, e);
		}
	}

	private MailSender() {
	}

	/**
	 * Sends message
	 * 
	 * @param email          {@link String} recipient email
	 * @param messageSubject {@link String} message subject
	 * @param messageText    {@link String} message text
	 * @return boolean true if if the message is sent, else false
	 */
	public static boolean send(String email, String messageSubject, String messageText) {
		if (email == null || messageSubject == null || messageText == null) {
			return false;
		}
		boolean result;
		Session mailSession = Session.getDefaultInstance(properties, authenticator);
		try {
			MimeMessage message = new MimeMessage(mailSession);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(messageSubject);
			message.setText(messageText);
			Transport.send(message);
			result = true;
		} catch (MessagingException e) {
			logger.error("error sending message", e);
			result = false;
		}
		return result;
	}
}
