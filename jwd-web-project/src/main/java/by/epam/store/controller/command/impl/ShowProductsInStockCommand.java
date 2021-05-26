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
import by.epam.store.entity.ProductList;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.UserControl;

public class ShowProductsInStockCommand implements Command {
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
		String page = request.getParameter(ParameterAndAttribute.PAGE);
		try {
			ProductList productList = catalogService.takeProductsInStock(page);
			if (!productList.getProducts().isEmpty()) {
				request.setAttribute(ParameterAndAttribute.PRODUCTS, productList.getProducts());
				request.setAttribute(ParameterAndAttribute.PAGE, productList.getCurrentPageNumber());
			    request.setAttribute(ParameterAndAttribute.NUMBER_OF_PAGES, productList.getNumberOfPages());
			} else {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_NOTHING_FOUND_MESSAGE);
			}
			request.setAttribute(ParameterAndAttribute.TITLE, MessageKey.TITLE_PRODUCTS_IN_STOCK);
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.SHOW_PRODUCTS_IN_STOCK);
			router = new Router(PagePath.PRODUCTS, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("products search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
