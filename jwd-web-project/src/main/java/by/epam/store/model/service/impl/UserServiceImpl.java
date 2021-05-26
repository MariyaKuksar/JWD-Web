package by.epam.store.model.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
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
import by.epam.store.util.PasswordEncryption;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.UserInfoValidator;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	private static final String BUNDLE_NAME = "path";
	private static final String PATH_APP = "path.app";
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
			String link = ResourceBundle.getBundle(BUNDLE_NAME).getString(PATH_APP) + PagePath.CONFIRM_REGISTRATION;
			MailSender.send(user.getLogin(), MessageKey.REGISTRATION_MESSAGE_SUBJECT,
					MessageKey.REGISTRATION_MESSAGE_TEXT + link + user.getUserId());
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
				MailSender.send(login, MessageKey.CHANGE_PASSWORD_MESSAGE_SUBJECT,
						MessageKey.CHANGE_PASSWORD_MESSAGE_TEXT + newPassword);
			}
		} catch (MessagingException | DaoException e) {
			throw new ServiceException("password change error", e);
		}
		return passwordChanged;
	}
	
	@Override
	public boolean changePassword(String login, String currentPassword, String newPassword)
			throws ServiceException, InvalidDataException {
		if (!UserInfoValidator.isValidLogin(login)) {
			throw new InvalidDataException("incorrect login",
					Arrays.asList(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE));
		}
		if (!UserInfoValidator.isValidPassword(newPassword)) {
			throw new InvalidDataException("incorrect password", Arrays.asList(MessageKey.ERROR_PASSWORD_MESSAGE));
		}
		if (!UserInfoValidator.isValidPassword(currentPassword)) {
			return false;
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
	
	@Override
	public boolean changeUserData(Map<String, String> userInfo) throws ServiceException, InvalidDataException {
		String userId = userInfo.get(ParameterAndAttribute.USER_ID);
		String currentLogin = userInfo.get(ParameterAndAttribute.CURRENT_LOGIN);
		if (!IdValidator.isValidId(userId) || !UserInfoValidator.isValidLogin(currentLogin)) {
			throw new InvalidDataException("impossible operation",
					Arrays.asList(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE));
		}
		List<String> errorMessageList = UserInfoValidator.findInvalidData(userInfo);
		String login = userInfo.get(ParameterAndAttribute.LOGIN);
		if (UserInfoValidator.isValidLogin(login) && !StringUtils.equals(login, currentLogin)
				&& !checkIfLoginFree(login)) {
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
	public boolean blockUser(String userId) throws ServiceException {
		if (!IdValidator.isValidId(userId)) {
			return false;
		}
		boolean userBlocked;
		try {
			userBlocked = userDao.changeUserStatus(Long.parseLong(userId), UserStatus.ACTIVE, UserStatus.BLOCKED);
		} catch (DaoException e) {
			throw new ServiceException("user blocking error", e);
		}
		return userBlocked;
	}

	@Override
	public boolean unblockUser(String userId) throws ServiceException {
		if (!IdValidator.isValidId(userId)) {
			return false;
		}
		boolean userUnblocked;
		try {
			userUnblocked = userDao.changeUserStatus(Long.parseLong(userId), UserStatus.BLOCKED, UserStatus.ACTIVE);
		} catch (DaoException e) {
			throw new ServiceException("user unblocking error", e);
		}
		return userUnblocked;
	}

	@Override
	public boolean sendMessage(String email, String message) throws ServiceException {
		if ((UserInfoValidator.isValidLogin(email) && checkIfLoginFree(email))) {
			return false;
		}
		try {
			MailSender.send(email, MessageKey.INFO_MESSAGE_SUBJECT,
					message);
		} catch (MessagingException e) {
			throw new ServiceException("message sending error", e);
		}
		return true;
	}

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
	
	@Override
	public Optional<User> takeUserById(String userId) throws ServiceException {
		if (!IdValidator.isValidId(userId)) {
			return Optional.empty();
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.findUserById(userId);
		} catch (DaoException e) {
			throw new ServiceException("user search error", e);
		}
		return userOptional;
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
