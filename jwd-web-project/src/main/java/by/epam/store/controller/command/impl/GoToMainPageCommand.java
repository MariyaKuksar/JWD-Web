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

public class GoToMainPageCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		//вытянуть каталог товаров для отображения
		HttpSession session = request.getSession(true);
		session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.GO_TO_MAIN_PAGE);
		Router router = new Router(PagePath.MAIN, RouteType.FORWARD);
		return router;
	}
}
