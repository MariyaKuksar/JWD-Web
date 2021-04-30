package by.epam.store.model.service.impl;

import java.util.Map;
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
import by.epam.store.model.entity.Product;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.validator.IdValidator;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger();
	private static final int AMOUNT_OF_PRODUCT = 1;
	private OrderDao orderDao = new OrderDaoImpl();
	private OrderProductConnectionDao orderProductConnectionDao = new OrderProductConnectionDaoImpl();

	@Override
	public Optional<Long> addProductToBasket(Long userId, Long orderBasketId, String productId)
			throws ServiceException {
		if (!IdValidator.isValidId(productId) || userId == null) {
			return Optional.empty();
		}
		try {
			if (orderBasketId == null) {
				orderBasketId = takeOrderBasketId(userId);
			}
			OrderProductConnection orderProductConnection = new OrderProductConnection(orderBasketId,
					Long.parseLong(productId), AMOUNT_OF_PRODUCT);
			if (!orderProductConnectionDao.update(orderProductConnection)) {
				orderProductConnectionDao.create(orderProductConnection);
			}
		} catch (DaoException e) {
			throw new ServiceException("product adding error", e);
		}
		return Optional.of(orderBasketId);
	}

	@Override
	public Optional<Order> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException {
		if (userId == null) {
			return Optional.empty();
		}
		Order order;
		try {
			if (orderBasketId == null) {
				orderBasketId = takeOrderBasketId(userId);
			}
			order = new Order(orderBasketId, userId);
			Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(orderBasketId);
			order.setProducts(products);
		} catch (DaoException e) {
			throw new ServiceException("product search error", e);
		}
		return Optional.of(order);
	}

	private Long takeOrderBasketId(Long userId) throws DaoException {
		Long orderBasketId;
		Optional<Long> orderBasketIdOptional = orderDao.findOrderBasketId(userId);
		if (orderBasketIdOptional.isPresent()) {
			orderBasketId = orderBasketIdOptional.get();
			logger.debug("order Basket Id = " + orderBasketId);
		} else {
			Order order = new Order(userId);
			orderDao.create(order);
			orderBasketId = order.getOrderId();
		}
		return orderBasketId;
	}
}