package by.epam.store.controller.command.impl;

import java.util.Arrays;

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

public class ForgotPasswordCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		String login = request.getParameter(ParameterAndAttribute.LOGIN);
		UserService userService = ServiceFactory.getInstance().getUserService();
		HttpSession session = request.getSession(true);
		Router router;
		try {
			if (userService.changeForgottenPassword(login)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_PASSWORD_SENT_MESSAGE);
				router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST, Arrays.asList(MessageKey.ERROR_NO_SUCH_USER_MESSAGE));
				router = new Router(PagePath.FORGOT_PASSWORD, RouteType.REDIRECT);
			}
		} catch (ServiceException e) {
			logger.error("change password error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}