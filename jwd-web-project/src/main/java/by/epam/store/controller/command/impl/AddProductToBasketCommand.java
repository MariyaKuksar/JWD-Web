package by.epam.store.controller.command.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.UserControl;

public class AddProductToBasketCommand implements Command {
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
		Long userId = (Long) session.getAttribute(ParameterAndAttribute.USER_ID);
		Long orderBasketId = (Long) session.getAttribute(ParameterAndAttribute.ORDER_BASKET_ID);
		String productId = request.getParameter(ParameterAndAttribute.PRODUCT_ID);
		try {
			Optional<Long> orderBasketIdOptional = orderService.addProductToBasket(userId, orderBasketId, productId);
			if (orderBasketIdOptional.isPresent()) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE,
						MessageKey.INFO_PRODUCT_ADDED_TO_BASKET_MESSAGE);
				session.setAttribute(ParameterAndAttribute.ORDER_BASKET_ID, orderBasketIdOptional.get());
				String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
				logger.debug(page);
				router = new Router(page, RouteType.REDIRECT);
			} else {
				logger.info("incorrect data");
				router = new Router(PagePath.ERROR, RouteType.REDIRECT);
			}
		} catch (ServiceException e) {
			logger.error("error adding product to basket", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
