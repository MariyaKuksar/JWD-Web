package by.epam.store.controller.command.impl;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.util.MessageKey;
import by.epam.store.util.UserControl;

public class FindOrderByIdCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.ADMIN)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		String orderId = request.getParameter(ParameterAndAttribute.ORDER_ID);
		try {
			Optional<Order> orderOptional = orderService.takeOrderById(orderId);
			if (orderOptional.isPresent()) {
				request.setAttribute(ParameterAndAttribute.ORDERS, Arrays.asList(orderOptional.get()));
			} else {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_NOTHING_FOUND_MESSAGE);
			}
			session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.FIND_ORDER_BY_ID + orderId);
			request.setAttribute(ParameterAndAttribute.ORDER_STATUS_LIST, Arrays.asList(OrderStatus.values()));
			router = new Router(PagePath.ORDERS, RouteType.FORWARD);
		} catch (ServiceException e) {
			logger.error("order search error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}
}
