package by.epam.store.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;

public class DefaultCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		return router;
	}
}
