package by.epam.store.controller.command.impl;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
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
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();
		Router router;
		try {
			HttpSession session = request.getSession(true);
			if (userService.activation(userId)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_REGISTRATION_OK_MESSAGE);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST,
						Arrays.asList(MessageKey.ERROR_USER_NOT_FOUND_MESSAGE));
			}
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "user activation error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
