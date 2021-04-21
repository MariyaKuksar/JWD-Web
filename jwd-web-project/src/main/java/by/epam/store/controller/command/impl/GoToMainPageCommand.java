package by.epam.store.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;

public class GoToMainPageCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
		Router router;
		try {
			List<ProductCategory> productCategories = catalogService.takeAllProductCategories();
			logger.debug(productCategories.toString());
			HttpSession session = request.getSession(true);
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.MAIN);
			session.setAttribute(ParameterAndAttribute.PRODUCT_CATEGORIES, productCategories);
			router = new Router(PagePath.MAIN, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("product categories search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
