package by.epam.store.controller.command.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.User;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.UserControl;

public class SignInCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		HttpSession session = request.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.LOGIN) != null) {
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_REPEATED_LOGIN_MESSAGE);
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		UserService userService = ServiceFactory.getInstance().getUserService();
		String login = request.getParameter(ParameterAndAttribute.LOGIN);
		String password = request.getParameter(ParameterAndAttribute.PASSWORD);
		try {
			Optional<User> userOptional = userService.authorization(login, password);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				UserControl.userStatusControl(user, session);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_LOGIN_MESSAGE);
			}
			String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
			logger.debug(page);
			router = new Router(page, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("user search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}