package by.epam.store.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
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
import by.epam.store.model.service.impl.UserServiceImpl;

//еще корявая реализация, надо доделывать
public class FindAllUsersCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	private static final String ATTRIBUTE_USERS = "users";

	@Override
	public Router execute(HttpServletRequest request) {
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();
		Router router;
		try {
			List<User> users = userService.findAllUsers();
			request.setAttribute(ATTRIBUTE_USERS, users);
			router = new Router(PagePath.USERS, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "users search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
