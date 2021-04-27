package by.epam.store.model.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.dao.impl.OrderDaoImpl;
import by.epam.store.model.entity.Order;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.validator.IdValidator;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger();
	private OrderDao orderDao = new OrderDaoImpl();

	@Override
	public boolean addProduct(String userId, String productId) throws ServiceException {
		if (!IdValidator.isValidId(productId) || !IdValidator.isValidId(userId)) {
			return false;
		}
	/*	Optional<Order> orderOptional = orderDao.findOrderBasket(userId);
		Order order;
		if (orderOptional.isEmpty()) {
			order = new Order();
			order.setUserId(Long.parseLong(userId));
			orderDao.create(order);
		} */
		return true;
	}
}
