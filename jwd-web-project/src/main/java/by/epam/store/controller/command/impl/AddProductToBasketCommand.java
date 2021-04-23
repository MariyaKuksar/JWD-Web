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
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.SessionControl;

public class AddProductToBasketCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!SessionControl.isLoggedInUser(request)) {
			router = new Router(PagePath.LOGIN, RouteType.REDIRECT);
			return router;
		}
		String productId = request.getParameter(ParameterAndAttribute.PRODUCT_ID);
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		try {
			if (orderService.addProductToOrder(productId)) {
				HttpSession session = request.getSession(true);
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_PRODUCT_ADDED_TO_BASKET_MESSAGE);
				String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
				router = new Router(page, RouteType.REDIRECT);
			} else {
				logger.info("product not added to order");
				router = new Router(PagePath.ERROR, RouteType.REDIRECT);
			}
		} catch (ServiceException e) {
			logger.error("error adding product to order", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
