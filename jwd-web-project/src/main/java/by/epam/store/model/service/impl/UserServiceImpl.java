package by.epam.store.model.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import by.epam.store.entity.User;
import by.epam.store.entity.UserRole;
import by.epam.store.entity.UserStatus;
import by.epam.store.entity.builder.UserBuilder;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.UserDao;
import by.epam.store.model.dao.impl.UserDaoImpl;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MailSender;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.PasswordEncryption;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.UserInfoValidator;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	private static final String REGISTRATION_MESSAGE_SUBJECT = "Confirmation of registration";
	private static final String REGISTRATION_MESSAGE_TEXT = "To confirm registration, follow the link http://localhost:8080/jwd-web-project/controller?command=confirm_registration&userId=";
	private static final String CHANGE_PASSWORD_MESSAGE_SUBJECT = "Change password";
	private static final String CHANGE_PASSWORD_MESSAGE_TEXT = "Your new login password is ";
	private static final int NUMBER_PASSWORD_CHARACTERS = 8;

	@Override
	public void registration(Map<String, String> userInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = UserInfoValidator.findInvalidData(userInfo);
		String login = userInfo.get(ParameterAndAttribute.LOGIN);
		if (UserInfoValidator.isValidLogin(login) && !checkIfLoginFree(login)) {
			errorMessageList.add(MessageKey.ERROR_LOGIN_IS_BUSY_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		try {
			String encryptedPassword = PasswordEncryption.encrypt(userInfo.get(ParameterAndAttribute.PASSWORD));
			userInfo.put(ParameterAndAttribute.PASSWORD, encryptedPassword);
			User user = UserBuilder.getInstance().build(userInfo);
			user.setRole(UserRole.CLIENT);
			user.setStatus(UserStatus.INACTIVE);
			userDao.create(user);
			MailSender.send(user.getLogin(), REGISTRATION_MESSAGE_SUBJECT,
					REGISTRATION_MESSAGE_TEXT + user.getUserId());
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("user creation error", e);
		}
	}

	@Override
	public boolean activation(String userId) throws ServiceException {
		if (!IdValidator.isValidId(userId)) {
			return false;
		}
		boolean userActivated;
		try {
			userActivated = userDao.changeUserStatus(Long.parseLong(userId), UserStatus.INACTIVE, UserStatus.ACTIVE);
		} catch (DaoException e) {
			throw new ServiceException("user activation error", e);
		}
		return userActivated;
	}

	@Override
	public Optional<User> authorization(String login, String password) throws ServiceException {
		if (!UserInfoValidator.isValidLogin(login) || !UserInfoValidator.isValidPassword(password)) {
			return Optional.empty();
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.findUserByLogin(login);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				String encryptedPassword = PasswordEncryption.encrypt(password);
				if (!StringUtils.equals(encryptedPassword, user.getPassword())) {
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
		if (!UserInfoValidator.isValidLogin(login)) {
			return false;
		}
		boolean passwordChanged;
		try {
			if (checkIfLoginFree(login)) {
				return false;
			}
			String newPassword = generatePassword();
			String encryptedPassword = PasswordEncryption.encrypt(newPassword);
			passwordChanged = userDao.updatePassword(login, encryptedPassword);
			if (passwordChanged) {
				MailSender.send(login, CHANGE_PASSWORD_MESSAGE_SUBJECT, CHANGE_PASSWORD_MESSAGE_TEXT + newPassword);
			}
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("password change error", e);
		}
		return passwordChanged;
	}

	// нужно доработать, если буду использовать
	@Override
	public List<User> takeAllUsers() throws ServiceException {
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
	public List<User> takeUsersByName(String userName) throws ServiceException {
		if (!UserInfoValidator.isValidName(userName)) {
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

	@Override
	public Optional<User> takeUserByLogin(String login) throws ServiceException {
		if (!UserInfoValidator.isValidLogin(login)) {
			return Optional.empty();
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.findUserByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException("user search error", e);
		}
		return userOptional;
	}
	
	@Override
	public boolean changeUserData(Map<String, String> userInfo) throws ServiceException, InvalidDataException {
		String userId = userInfo.get(ParameterAndAttribute.USER_ID);
		String currentLogin = userInfo.get(ParameterAndAttribute.CURRENT_LOGIN);
		if (!IdValidator.isValidId(userId) || !UserInfoValidator.isValidLogin(currentLogin)) {
			throw new InvalidDataException("impossible operation", Arrays.asList(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE));
		}
		List<String> errorMessageList = UserInfoValidator.findInvalidData(userInfo);
		String login = userInfo.get(ParameterAndAttribute.LOGIN);
		if (UserInfoValidator.isValidLogin(login) && !StringUtils.equals(login, currentLogin)  && !checkIfLoginFree(login)) {
			errorMessageList.add(MessageKey.ERROR_LOGIN_IS_BUSY_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		boolean userChanged;
		try {
			String encryptedPassword = PasswordEncryption.encrypt(userInfo.get(ParameterAndAttribute.PASSWORD));
			userInfo.put(ParameterAndAttribute.PASSWORD, encryptedPassword);
			User user = UserBuilder.getInstance().build(userInfo);
			user.setUserId(Long.parseLong(userId));
			userChanged = userDao.update(user);
		} catch (DaoException e) {
			throw new ServiceException("user chanding error", e);
		}
		return userChanged;
	}
	
	@Override
	public boolean changePassword(String login, String currentPassword, String newPassword)
			throws ServiceException, InvalidDataException {
		if(!UserInfoValidator.isValidLogin(login)) {
			throw new InvalidDataException("incorrect login", Arrays.asList(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE));
		}
		if(!UserInfoValidator.isValidPassword(newPassword) || !UserInfoValidator.isValidPassword(currentPassword)) {
			throw new InvalidDataException("incorrect password", Arrays.asList(MessageKey.ERROR_PASSWORD_MESSAGE));
		}
		boolean passwordChanged;
		try {
			String encryptedPassword = PasswordEncryption.encrypt(currentPassword);
			String encryptedNewPassword = PasswordEncryption.encrypt(newPassword);
			passwordChanged = userDao.updatePassword(login, encryptedNewPassword, encryptedPassword);
		} catch (DaoException e) {
			throw new ServiceException("password change error", e);
		}
		return passwordChanged;
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
