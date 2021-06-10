package by.epam.store.controller.command.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.UserControl;

/**
 * The command is responsible for changing product data
 * 
 * @author Mariya Kuksar
 * @see Command
 */
public class ChangeProductDataCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.ADMIN)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
		CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
		Map<String, String> productInfo = RequestUtil.getRequestParameters(request);
		try {
			if (catalogService.changeProductData(productInfo)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_SAVED_SUCCESSFULLY_MESSAGE);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_SAVE_MESSAGE);
			}
			router = new Router(page, RouteType.REDIRECT);
		} catch (InvalidDataException e) {
			logger.error("invalid data", e);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, e.getErrorDescription());
			router = new Router(page, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("error changing product data", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}

}
