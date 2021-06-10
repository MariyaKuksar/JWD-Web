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
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.UserControl;

/**
 * The command is responsible for checkout
 * 
 * @author Mariya Kuksar
 * @see Command
 */
public class CheckoutCommand implements Command {
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
		Map<String, String> orderInfo = RequestUtil.getRequestParameters(request);
		String orderBasketId = String.valueOf(session.getAttribute(ParameterAndAttribute.ORDER_BASKET_ID));
		orderInfo.put(ParameterAndAttribute.ORDER_BASKET_ID, orderBasketId);
		try {
			if (orderService.checkout(orderInfo)) {
				session.removeAttribute(ParameterAndAttribute.ORDER_BASKET_ID);
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_ORDER_PLACED_MESSAGE);
				router = new Router(PagePath.GO_TO_ORDERS_PAGE, RouteType.REDIRECT);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
				router = new Router(PagePath.GO_TO_BASKET_PAGE, RouteType.REDIRECT);
			}
		} catch (InvalidDataException e) {
			logger.error("invalid data", e);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, e.getErrorDescription());
			router = new Router(PagePath.GO_TO_BASKET_PAGE, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("checkout error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
