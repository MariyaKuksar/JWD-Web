package by.epam.store.controller.command.impl;

import java.io.IOException;
import java.util.HashMap;
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
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.UserControl;

public class AddProductToCatalogCommand implements Command {
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
		Map<String, String> productInfo = new HashMap<String, String>();
		try {
			productInfo = RequestUtil.getParametersFromMultipartRequest(request);
			catalogService.addProduct(productInfo);
			session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_PRODUCT_ADDED_TO_CATALOG_MESSAGE);
			String categoryId = request.getParameter(ParameterAndAttribute.CATEGORY_ID);
			router = new Router(PagePath.SHOW_PRODUCTS_FROM_CATEGORY + categoryId, RouteType.REDIRECT);
		} catch (InvalidDataException e) {
			logger.error("invalid data", e);
			FileUtil.deleteFile(
					productInfo.get(ParameterAndAttribute.PATH) + productInfo.get(ParameterAndAttribute.IMAGE_NAME));
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, e.getErrorDescription());
			router = new Router(PagePath.ADDED_PRODUCT, RouteType.REDIRECT);
		} catch (IOException | ServletException | ServiceException e) {
			logger.error("error adding a product to the catalog", e);
			FileUtil.deleteFile(
					productInfo.get(productInfo.get(ParameterAndAttribute.PATH) + ParameterAndAttribute.IMAGE_NAME));
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
