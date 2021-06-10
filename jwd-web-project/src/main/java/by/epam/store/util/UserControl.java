package by.epam.store.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.User;
import by.epam.store.entity.UserRole;

/**
 * The utility is responsible for controlling the user
 * 
 * @author Mariya Kuksar
 */
public final class UserControl {
	private static final Logger logger = LogManager.getLogger();

	private UserControl() {
	}

	/**
	 * Checks if there is a logged in user
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return boolean true if there is a logged in user, else false
	 */
	public static boolean isLoggedInUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_SESSION_TIMED_OUT_MESSAGE);
			return false;
		}
		if (session.getAttribute(ParameterAndAttribute.LOGIN) == null) {
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_NEED_TO_LOGIN_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Checks if the role of the logged in user is appropriate
	 * 
	 * @param request         {@link HttpServletRequest}
	 * @param permissibleRole {@link UserRole} permissible user role
	 * @return boolean true if the role of the logged in user is appropriate, else
	 *         false
	 */
	public static boolean isValidForRole(HttpServletRequest request, UserRole permissibleRole) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.ROLE) != permissibleRole) {
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Controls the status of the user who logs in
	 * 
	 * @param user    {@link User} who logs in
	 * @param session {@link HttpSession}
	 */
	public static void userStatusControl(User user, HttpSession session) {
		switch (user.getStatus()) {
		case ACTIVE:
			session.setAttribute(ParameterAndAttribute.ROLE, user.getRole());
			session.setAttribute(ParameterAndAttribute.LOGIN, user.getLogin());
			session.setAttribute(ParameterAndAttribute.USER_ID, user.getUserId());
			break;
		case INACTIVE:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_UNVERIFIED_USER_MESSAGE);
			break;
		case BLOCKED:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_BLOCKED_USER_MESSAGE);
			break;
		default:
			logger.error("unknown user status: " + user.getStatus());
		}
	}
}