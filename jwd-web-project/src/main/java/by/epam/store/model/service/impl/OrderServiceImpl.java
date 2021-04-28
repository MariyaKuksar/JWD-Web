package by.epam.store.model.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.dao.impl.OrderDaoImpl;
import by.epam.store.model.dao.impl.OrderProductConnectionDaoImpl;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.OrderProductConnection;
import by.epam.store.model.entity.OrderStatus;
import by.epam.store.model.entity.Product;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.validator.IdValidator;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger();
	private OrderDao orderDao = new OrderDaoImpl();
	private OrderProductConnectionDao orderProductConnectionDao = new OrderProductConnectionDaoImpl();

	@Override
	public Optional<Long> addProduct(Long userId, Long orderBasketId, String productId) throws ServiceException {
		if (!IdValidator.isValidId(productId) || userId == null) {
			return Optional.empty();
		}
		try {
			if (orderBasketId == null) {
				orderBasketId = findOrderBasketId(userId);
			}
			Long idProduct = Long.parseLong(productId);
			OrderProductConnection orderProductConnection = new OrderProductConnection(orderBasketId,
					new Product(idProduct), 1); // нужно в константу выносить?
			if (!orderProductConnectionDao.update(orderProductConnection)) {
				orderProductConnectionDao.create(orderProductConnection);
			}
		} catch (DaoException e) {
			throw new ServiceException("product adding error", e);
		}
		return Optional.of(orderBasketId);
	}

	private Long findOrderBasketId(Long userId) throws DaoException {
		Long orderBasketId;
		Optional<Long> orderBasketIdOptional = orderDao.findOrderBasketId(userId);
		if (orderBasketIdOptional.isPresent()) {
			orderBasketId = orderBasketIdOptional.get();
			logger.debug("order Basket Id = " +  orderBasketId);
		} else {
			Order order = new Order(userId, OrderStatus.BASKET);
			orderDao.create(order);
			orderBasketId = order.getOrderId();
		}
		return orderBasketId;
	}
}