package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;

public final class UserInfoValidator {
	private static final String LOGIN_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{5,15}$";
	private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я]{1,20}$";
	private static final String PHONE_PATTERN = "^\\+375[0-9]{9}$";

	private UserInfoValidator() {
	}

	public static List<String> findInvalidData(Map<String, String> userInfo) {
		List<String> errorMessageList = new ArrayList<>();
		if (!isValidName(userInfo.get(ParameterAndAttribute.USER_NAME))) {
			errorMessageList.add(MessageKey.ERROR_NAME_MESSAGE);
		}
		if (!isValidPhone(userInfo.get(ParameterAndAttribute.PHONE))) {
			errorMessageList.add(MessageKey.ERROR_PHONE_MESSAGE);
		}
		if (!isValidLogin(userInfo.get(ParameterAndAttribute.LOGIN))) {
			errorMessageList.add(MessageKey.ERROR_EMAIL_MESSAGE);
		}
		if (!isValidPassword(userInfo.get(ParameterAndAttribute.PASSWORD))) {
			errorMessageList.add(MessageKey.ERROR_PASSWORD_MESSAGE);
		}
		return errorMessageList;
	}

	public static boolean isValidLogin(String login) {
		return (login != null) ? login.matches(LOGIN_PATTERN) : false;
	}

	public static boolean isValidPassword(String password) {
		return (password != null) ? password.matches(PASSWORD_PATTERN) : false;
	}

	public static boolean isValidName(String name) {
		return (name != null) ? name.matches(NAME_PATTERN) : false;
	}

	public static boolean isValidPhone(String phone) {
		return (phone != null) ? phone.matches(PHONE_PATTERN) : false;
	}
}
