package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.User;
import by.epam.store.util.MessageKey;

/**
 * Validates user info
 * 
 * @author Mariya Kuksar
 */
public final class UserInfoValidator {
	private static final String LOGIN_PATTERN = "^([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)$";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z\\d]{5,15}$";
	private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я-\\s]{1,45}$";
	private static final String PHONE_PATTERN = "^\\+375\\d{9}$";

	private UserInfoValidator() {
	}

	/**
	 * Looking for invalid user data
	 * 
	 * @param userInfo {@link Map} of {@link String} and {@link String} the names of
	 *                 the {@link User} fields and its values
	 * @return {@link List} of {@link String} error messages if user info is
	 *         invalid, else emptyList
	 */
	public static List<String> findInvalidData(Map<String, String> userInfo) {
		List<String> errorMessageList = new ArrayList<>();
		if (MapUtils.isEmpty(userInfo)) {
			errorMessageList.add(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return errorMessageList;
		}
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

	/**
	 * Checks if login is valid
	 * 
	 * @param login {@link String}
	 * @return boolean true if login is valid, else false
	 */
	public static boolean isValidLogin(String login) {
		return (login != null) ? login.matches(LOGIN_PATTERN) : false;
	}

	/**
	 * Checks if password is valid
	 * 
	 * @param password {@link String}
	 * @return boolean true if password is valid, else false
	 */
	public static boolean isValidPassword(String password) {
		return (password != null) ? password.matches(PASSWORD_PATTERN) : false;
	}

	/**
	 * Checks if name is valid
	 * 
	 * @param name {@link String}
	 * @return boolean true if name is valid, else false
	 */
	public static boolean isValidName(String name) {
		return (name != null) ? name.matches(NAME_PATTERN) : false;
	}

	/**
	 * Checks if phone is valid
	 * 
	 * @param phone {@link String}
	 * @return boolean true if phone is valid, else false
	 */
	public static boolean isValidPhone(String phone) {
		return (phone != null) ? phone.matches(PHONE_PATTERN) : false;
	}
}
