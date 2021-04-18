package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;

import by.epam.store.model.entity.User;
import by.epam.store.util.MessageKey;

public final class UserDataValidator {
	private static final String LOGIN_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{5,15}$";
	private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я]+$";
	private static final String PHONE_PATTERN = "^\\+375[0-9]{9}$";

	private UserDataValidator() {
	}

	public static boolean isValidLogin(String login) {
		boolean isValidLogin;
		if (login != null) {
			isValidLogin = login.matches(LOGIN_PATTERN);
		} else {
			isValidLogin = Boolean.FALSE;
		}
		return isValidLogin;
	}

	public static boolean isValidPassword(String password) {
		boolean isValidPassword;
		if (password != null) {
			isValidPassword = password.matches(PASSWORD_PATTERN);
		} else {
			isValidPassword = Boolean.FALSE;
		}
		return isValidPassword;
	}

	public static boolean isValidName(String name) {
		boolean isValidName;
		if (name != null) {
			isValidName = name.matches(NAME_PATTERN);
		} else {
			isValidName = Boolean.FALSE;
		}
		return isValidName;
	}

	public static boolean isValidPhone(String phone) {
		boolean isValidPhone;
		if (phone != null) {
			isValidPhone = phone.matches(PHONE_PATTERN);
		} else {
			isValidPhone = Boolean.FALSE;
		}
		return isValidPhone;
	}

	public static List<String> getErrorMessageList(User user) {
		List<String> errorMessageList = new ArrayList<>();
		if (!isValidName(user.getName())) {
			errorMessageList.add(MessageKey.ERROR_NAME_MESSAGE);
		}
		if (!isValidPhone(user.getPhone())) {
			errorMessageList.add(MessageKey.ERROR_PHONE_MESSAGE);
		}

		if (!isValidLogin(user.getLogin())) {
			errorMessageList.add(MessageKey.ERROR_EMAIL_MESSAGE);
		}
		if (!isValidPassword(user.getPassword())) {
			errorMessageList.add(MessageKey.ERROR_PASSWORD_MESSAGE);
		}
		return errorMessageList;
	}
}
