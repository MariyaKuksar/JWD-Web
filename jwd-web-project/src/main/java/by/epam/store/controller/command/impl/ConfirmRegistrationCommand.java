package by.epam.store.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;

public class ConfirmRegistrationCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		String userId = request.getParameter(ParameterAndAttribute.USER_ID);
		UserService userService = ServiceFactory.getInstance().getUserService();
		Router router;
		try {
			HttpSession session = request.getSession(true);
			if (userService.activation(userId)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_REGISTRATION_OK_MESSAGE);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE,
						MessageKey.ERROR_USER_TO_ACTIVATE_NOT_FOUND_MESSAGE);
			}
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("user activation error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
