package by.epam.store.model.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.UserDao;
import by.epam.store.model.dao.impl.UserDaoImpl;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserStatus;
import by.epam.store.model.entity.builder.UserBuilder;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MailSender;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.PasswordEncryption;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.UserDataValidator;

public class UserServiceImpl implements UserService {
	private static final Logger logger = LogManager.getLogger();
	private UserDao userDao = new UserDaoImpl();
	private static final String REGISTRATION_MESSAGE_SUBJECT = "Confirmation of registration";
	private static final String REGISTRATION_MESSAGE_TEXT = "To confirm registration, follow the link http://localhost:8080/jwd-web-project/controller?command=confirm_registration&userId=";
	private static final String CHANGE_PASSWORD_MESSAGE_SUBJECT = "Change password";
	private static final String CHANGE_PASSWORD_MESSAGE_TEXT = "Your new login password is ";
	private static final int NUMBER_PASSWORD_CHARACTERS = 8;

	@Override
	public List<String> registration(Map<String, String> userInfo) throws ServiceException {
		List<String> errorMessageList = UserDataValidator.getErrorMessageList(userInfo);
		String login = userInfo.get(ParameterAndAttribute.LOGIN);
		if (UserDataValidator.isValidLogin(login) && !checkIfLoginFree(login)) {
			errorMessageList.add(MessageKey.ERROR_LOGIN_IS_BUSY_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			return errorMessageList;
		}
		try {
			String encryptedPassword = PasswordEncryption.encrypt(userInfo.get(ParameterAndAttribute.PASSWORD));
			userInfo.put(ParameterAndAttribute.PASSWORD, encryptedPassword);
			User user = UserBuilder.getInstance().build(userInfo);
			userDao.create(user);
			MailSender.send(user.getLogin(), REGISTRATION_MESSAGE_SUBJECT,
					REGISTRATION_MESSAGE_TEXT + user.getUserId());
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("user creation error", e);
		}
		return errorMessageList;
	}

	@Override
	public boolean activation(String userId) throws ServiceException {
		if (!IdValidator.isValidId(userId)) {
			return false;
		}
		boolean userActivated;
		try {
			userActivated = userDao.changeUserStatus(userId, UserStatus.INACTIVE, UserStatus.ACTIVE);
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
				if (!StringUtils.equals(encryptedPassword, user.getPassword())) {
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
	public boolean changeForgottenPassword(String login) throws ServiceException {
		if (!UserDataValidator.isValidLogin(login)) {
			logger.debug("incorrect login");
			return false;
		}
		try {
			Optional<User> userOptional = userDao.findUserByLogin(login);
			if (userOptional.isEmpty()) {
				logger.debug("not such user");
				return false;
			}
			User user = userOptional.get();
			String newPassword = generatePassword();
			String encryptedPassword = PasswordEncryption.encrypt(newPassword);
			user.setPassword(encryptedPassword);
			if (userDao.update(user)) {
				MailSender.send(user.getLogin(), CHANGE_PASSWORD_MESSAGE_SUBJECT,
						CHANGE_PASSWORD_MESSAGE_TEXT + newPassword);
			} else {
				throw new ServiceException("password change error");
			}
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("password change error", e);
		}
		return true;
	}

	// нужно доработать, если буду использовать
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

	// нужно доработать, если буду использовать
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

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(NUMBER_PASSWORD_CHARACTERS);
	}
}
