package by.epam.store.controller.command.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.UserControl;

public class AddProductToCatalogCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request)) {
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			return router;
		}
		if (!UserControl.isValidForRole(request, UserRole.ADMIN)) {
			router = new Router(PagePath.MAIN, RouteType.REDIRECT);
			return router;
		}
		try {
			Map<String, String> productInfo = RequestUtil.getParametersFromMultipartRequest(request);
			CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
			List<String> errorMessageList = catalogService.addProduct(productInfo);
			HttpSession session = request.getSession(true);
			if (errorMessageList.isEmpty()) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE,
						MessageKey.INFO_PRODUCT_ADDED_TO_CATALOG_MESSAGE);
				String categoryId = request.getParameter(ParameterAndAttribute.CATEGORY_ID);
				router = new Router(PagePath.SHOW_PRODUCTS_FROM_CATEGORY + categoryId, RouteType.REDIRECT);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, errorMessageList);
				router = new Router(PagePath.ADDED_PRODUCT, RouteType.REDIRECT);
			}
		} catch (IOException | ServletException e) {
			logger.error("file save error");
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("error adding a product to the catalog");
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
