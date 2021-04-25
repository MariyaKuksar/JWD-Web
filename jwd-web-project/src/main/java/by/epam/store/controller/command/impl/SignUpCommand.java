package by.epam.store.controller.command.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.RequestUtil;

public class SignUpCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Map<String, String> userInfo = RequestUtil.getRequestParameters(request);
		UserService userService = ServiceFactory.getInstance().getUserService();
		Router router;
		try {
			List<String> errorMessageList = userService.registration(userInfo);
			HttpSession session = request.getSession(true);
			if (errorMessageList.isEmpty()) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE,
						MessageKey.INFO_CONFIRMATION_OF_REGISTRATION_MESSAGE);
				router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, errorMessageList);
				router = new Router(PagePath.REGISTRATION, RouteType.REDIRECT);
			}
		} catch (ServiceException e) {
			logger.error("user creation error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
