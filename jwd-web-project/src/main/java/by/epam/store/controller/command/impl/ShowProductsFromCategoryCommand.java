package by.epam.store.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.Product;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.ParameterAndAttribute;

public class ShowProductsFromCategoryCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		String categoryId = request.getParameter(ParameterAndAttribute.CATEGORY_ID);
		CatalogService productService = ServiceFactory.getInstance().getCatalogService();
		Router router;
		try {
			List<Product> products = productService.takeProductsFromCategory(categoryId);
			logger.debug(products.toString());
			request.setAttribute(ParameterAndAttribute.PRODUCTS, products);
			HttpSession session = request.getSession(true);
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.SHOW_PRODUCTS_FROM_CATEGORY + categoryId);
			router = new Router(PagePath.MAIN, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("products from category search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
