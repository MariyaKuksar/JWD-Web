package by.epam.store.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.User;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;

//еще корявая реализация, надо доделывать
public class FindUsersByNameCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String PARAMETR_USER_NAME = "name";
	private static final String ATTRIBUTE_USERS = "users";

	@Override
	public Router execute(HttpServletRequest request) {
		String userName = request.getParameter(PARAMETR_USER_NAME);
		UserService userService = ServiceFactory.getInstance().getUserService();
		Router router;
		try {
			List<User> users = userService.takeUsersByName(userName);
			request.setAttribute(ATTRIBUTE_USERS, users);
			router = new Router(PagePath.USERS, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("users search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
