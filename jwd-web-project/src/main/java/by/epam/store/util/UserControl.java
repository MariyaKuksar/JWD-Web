package by.epam.store.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.User;
import by.epam.store.entity.UserRole;

public final class UserControl {
	private static final Logger logger = LogManager.getLogger();

	private UserControl() {
	}

	public static boolean isLoggedInUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			logger.info("session timed out");
			session = request.getSession(true);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_SESSION_TIMED_OUT_MESSAGE);
			return false;
		}
		if (session.getAttribute(ParameterAndAttribute.LOGIN) == null) {
			logger.info("not logged in user");
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_NEED_TO_LOGIN_MESSAGE);
			return false;
		}
		return true;
	}

	public static boolean isValidForRole(HttpServletRequest request, UserRole permissibleRole) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.ROLE) != permissibleRole) {
			logger.info("impossible operation for user role");
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return false;
		}
		return true;
	}

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
			logger.error("unknown user status");
		}
	}
}