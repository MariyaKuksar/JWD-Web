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
import by.epam.store.entity.Product;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;

/**
 * The command is responsible for search products from category
 * 
 * @author Mariya Kuksar
 * @see Command
 */
public class ShowProductsFromCategoryCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		HttpSession session = request.getSession(true);
		CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
		String categoryId = request.getParameter(ParameterAndAttribute.CATEGORY_ID);
		String sortingMethod = request.getParameter(ParameterAndAttribute.SORTING_METHOD);
		try {
			List<Product> products = catalogService.takeProductsFromCategory(categoryId, sortingMethod);
			if (!products.isEmpty()) {
				request.setAttribute(ParameterAndAttribute.PRODUCTS, products);
				request.setAttribute(ParameterAndAttribute.CATEGORY_ID, categoryId);
			} else {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_NOTHING_FOUND_MESSAGE);
			}
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.SHOW_PRODUCTS_FROM_CATEGORY + categoryId);
			router = new Router(PagePath.MAIN, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("products from category search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
