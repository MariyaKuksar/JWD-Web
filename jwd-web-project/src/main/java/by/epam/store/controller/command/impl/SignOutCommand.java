package by.epam.store.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;

public class SignOutCommand implements Command {

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return router;
	}
}
