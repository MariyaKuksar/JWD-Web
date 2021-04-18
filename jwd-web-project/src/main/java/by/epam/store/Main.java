package by.epam.store;

import javax.mail.MessagingException;

import by.epam.store.util.MailSender;

public class Main {

	public static void main(String[] args) throws MessagingException {
	MailSender.send("thrhgfh@gmail.com", "URL", "http://localhost:8080/jwd-web-project/jsp/login.jsp");
	}

}
