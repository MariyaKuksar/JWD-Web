package by.epam.store.controller.command.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.Product;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.UserControl;

public class AcceptProductsCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.ADMIN)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
		@SuppressWarnings("unchecked") // TODO компилятор предупреждает что непроверенное привидение и требует эту
										// аннотацию, как тут быть?
		Map<Product, Integer> suppliedProducts = (Map<Product, Integer>) session
				.getAttribute(ParameterAndAttribute.SUPPLIED_PRODUCTS);
		try {
			if (catalogService.acceptProducts(suppliedProducts)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_PRODUCT_ACCEPTED_MESSAGE);
				session.removeAttribute(ParameterAndAttribute.SUPPLIED_PRODUCTS);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE,
						MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			}
			router = new Router(PagePath.SUPPLY, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("error accepting products", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
