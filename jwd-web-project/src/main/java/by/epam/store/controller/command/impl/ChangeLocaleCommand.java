package by.epam.store.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.util.ParameterAndAttribute;

public class ChangeLocaleCommand implements Command {
	// private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		HttpSession session = request.getSession(true);
		session.setAttribute(ParameterAndAttribute.LOCALE, request.getParameter(ParameterAndAttribute.COMMAND));
		String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);	
		if (page != null) {
			router = new Router(page, RouteType.REDIRECT);
		} else {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
		}
		return router;
	}
}
