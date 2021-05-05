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

public class MailSender {
	private static final Logger logger = LogManager.getLogger();
	private static final String MAIL_PROPERTIES = "mail.properties";
	private static final String MAIL_USER_NAME = "mail.user.name";
	private static final String MAIL_USER_PASSWORD = "mail.user.password";
	private static final Properties properties = new Properties();

	static {
		ClassLoader classLoader = MailSender.class.getClassLoader();
		try (InputStream resourceAsStream = classLoader.getResourceAsStream(MAIL_PROPERTIES)) {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			logger.error("Error uploading" + MAIL_PROPERTIES, e);
			throw new RuntimeException("Error uploading a file" + MAIL_PROPERTIES, e);
		}
	}

	private MailSender() {
	}

	public static void send(String email, String messageSubject, String messageText) throws MessagingException {
		//TODO нужно проверять параметры на null????
		Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty(MAIL_USER_NAME),
						properties.getProperty(MAIL_USER_PASSWORD));
			}
		});
		MimeMessage message = new MimeMessage(mailSession);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject(messageSubject);
		message.setText(messageText);
		Transport.send(message);
	}
}
