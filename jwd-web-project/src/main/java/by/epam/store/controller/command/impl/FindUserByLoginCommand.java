package by.epam.store.controller.command.impl;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.User;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.UserControl;

public class FindUserByLoginCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.ADMIN)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		UserService userService = ServiceFactory.getInstance().getUserService();
		String login = request.getParameter(ParameterAndAttribute.LOGIN);
		try {
			Optional<User> userOptional = userService.takeUserByLogin(login);
			if (userOptional.isPresent() && userOptional.get().getRole() == UserRole.CLIENT) {
				request.setAttribute(ParameterAndAttribute.USERS, Arrays.asList(userOptional.get()));
			} else {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_NOTHING_FOUND_MESSAGE);
			}
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.FIND_USER_BY_LOGIN + login);
			router = new Router(PagePath.CLIENTS, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("user search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
