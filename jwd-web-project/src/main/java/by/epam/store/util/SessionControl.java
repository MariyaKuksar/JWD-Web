package by.epam.store.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.User;

public final class SessionControl {
	private static final Logger logger = LogManager.getLogger();

	private SessionControl() {
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

	public static Router userStatusControl(User user, HttpSession session) {
		Router router;
		switch (user.getStatus()) {
		case ACTIVE:
			session.setAttribute(ParameterAndAttribute.ROLE, user.getRole());
			session.setAttribute(ParameterAndAttribute.LOGIN, user.getLogin());
			session.setAttribute(ParameterAndAttribute.USER_ID, user.getUserId());
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.MAIN);
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			break;
		case INACTIVE:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_UNVERIFIED_USER_MESSAGE);
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			break;
		case BLOCKED:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_BLOCKED_USER_MESSAGE);
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			break;
		default:
			logger.error("unknown user status");
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}