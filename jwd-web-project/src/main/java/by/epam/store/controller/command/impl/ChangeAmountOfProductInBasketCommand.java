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
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.UserControl;

public class ChangeAmountOfProductInBasketCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.CLIENT)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		Long orderBasketId = (Long) session.getAttribute(ParameterAndAttribute.ORDER_BASKET_ID);
		String productId = request.getParameter(ParameterAndAttribute.PRODUCT_ID);
		String amountProduct = request.getParameter(ParameterAndAttribute.AMOUNT_PRODUCT);
		try {
			if(orderService.changeAmountOfProductInOrder(orderBasketId, productId, amountProduct)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_SAVED_SUCCESSFULLY_MESSAGE);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_SAVE_MESSAGE);
			}
			router = new Router(PagePath.GO_TO_BASKET_PAGE, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("error changing amount of product in basket", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}

}
