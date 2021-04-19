package by.epam.store.model.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.UserDao;
import by.epam.store.model.dao.impl.UserDaoImpl;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.entity.UserStatus;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MailSender;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PasswordEncryption;
import by.epam.store.validator.UserDataValidator;

public class UserServiceImpl implements UserService {
	private static final Logger logger = LogManager.getLogger();
	private UserDao userDao = new UserDaoImpl();
	private static final String REGISTRATION_MESSAGE_SUBJECT = "Confirmation of registration";
	private static final String REGISTRATION_MESSAGE_TEXT = "To confirm registration, follow the link http://localhost:8080/jwd-web-project/controller?command=confirm_registration&userId=";

	@Override
	public List<String> registration(User user) throws ServiceException {
		List<String> errorMessageList = UserDataValidator.getErrorMessageList(user);
		String login = user.getLogin();
		if (UserDataValidator.isValidLogin(login) && !checkIfLoginFree(login)) {
			errorMessageList.add(MessageKey.ERROR_LOGIN_IS_BUSY_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			return errorMessageList;
		}
		try {
			String encryptedPassword = PasswordEncryption.encrypt(user.getPassword());
			user.setPassword(encryptedPassword);
			user.setRole(UserRole.CLIENT);
			user.setStatus(UserStatus.INACTIVE);
			long userId = userDao.create(user);
			MailSender.send(user.getLogin(), REGISTRATION_MESSAGE_SUBJECT,
					REGISTRATION_MESSAGE_TEXT + userId);
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("user creation error", e);
		}
		return errorMessageList;
	}
	
	@Override
	public boolean activation(String userId) throws ServiceException {
		boolean userActivated;
		try {
			long id = Long.parseLong(userId);
			userActivated = userDao.changeUserStatus(id, UserStatus.INACTIVE, UserStatus.ACTIVE);
		} catch (NumberFormatException e) { 
			logger.info ("userId incorrect");
			userActivated = false;
		} catch (DaoException e) {
			throw new ServiceException("user activation error", e);
		}	
		return userActivated;
	}
	
	@Override
	public Optional<User> authorization(String login, String password) throws ServiceException {
		if (!UserDataValidator.isValidLogin(login) || !UserDataValidator.isValidPassword(password)) {
			logger.debug("is not Valid Login or Password");
			return Optional.empty();
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.findUserByLogin(login);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				String encryptedPassword = PasswordEncryption.encrypt(password);
				if (!StringUtils.equals(encryptedPassword,user.getPassword())) {
					logger.debug("incorrect Password");
					userOptional = Optional.empty();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("users search error", e);
		}
		return userOptional;
	}

	@Override
	public List<User> findAllUsers() throws ServiceException {
		List<User> users;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("users search error", e);
		}
		return users;
	}

	@Override
	public List<User> findUsersByName(String userName) throws ServiceException {
		if (!UserDataValidator.isValidName(userName)) {
			return Collections.emptyList();
		}
		List<User> users;
		try {
			users = userDao.findUsersByName(userName);
		} catch (DaoException e) {
			throw new ServiceException("users search error", e);
		}
		return users;
	}

	private boolean checkIfLoginFree(String login) throws ServiceException {
		Optional<User> userOptional;
		try {
			userOptional = userDao.findUserByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException("impossible to check whether the login is free", e);
		}
		return userOptional.isEmpty();
	}
}
