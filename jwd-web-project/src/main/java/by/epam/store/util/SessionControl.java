package by.epam.store.util;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
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

	public static boolean isLoggedInUser(HttpSession session) {
		boolean isLoggedInUser;
		if (session.getAttribute(ParameterAndAttribute.LOGIN) == null) {
			isLoggedInUser = false;
		} else {
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST,
					Arrays.asList(MessageKey.ERROR_REPEATED_LOGIN_MESSAGE));
			isLoggedInUser = true;
		}
		return isLoggedInUser;
	}

	public static Router userStatusControl(User user, HttpSession session) {
		Router router;
		switch (user.getStatus()) {
		case ACTIVE:
			session.setAttribute(ParameterAndAttribute.ROLE, user.getRole());
			session.setAttribute(ParameterAndAttribute.LOGIN, user.getLogin());
			session.setAttribute(ParameterAndAttribute.USER_ID, user.getUserId());
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.MAIN);
			router = new Router(PagePath.MAIN, RouteType.REDIRECT);
			break;
		case INACTIVE:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST,
					Arrays.asList(MessageKey.ERROR_UNVERIFIED_USER_MESSAGE));
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			break;
		case BLOCKED:
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST,
					Arrays.asList(MessageKey.ERROR_BLOCKED_USER_MESSAGE));
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			break;
		default:
			logger.log(Level.ERROR, "unknown user status");
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}