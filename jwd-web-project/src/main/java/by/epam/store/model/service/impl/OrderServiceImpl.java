package by.epam.store.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderProductConnection;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.Product;
import by.epam.store.entity.UserRole;
import by.epam.store.entity.builder.impl.OrderBuilder;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.impl.OrderDaoImpl;
import by.epam.store.model.dao.impl.OrderProductConnectionDaoImpl;
import by.epam.store.model.dao.impl.ProductDaoImpl;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.util.PriceCalculator;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.OrderInfoValidator;
import by.epam.store.validator.ProductInfoValidator;
import by.epam.store.validator.UserInfoValidator;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger();
	private static final int ONE_PRODUCT = 1;
	private OrderDao orderDao = new OrderDaoImpl();
	private OrderProductConnectionDao orderProductConnectionDao = new OrderProductConnectionDaoImpl();
	private ProductDao productDao = new ProductDaoImpl();

	@Override
	public Optional<Order> addProductToBasket(Long userId, Long orderBasketId, String productId)
			throws ServiceException {
		if (userId == null || !IdValidator.isValidId(productId)) {
			return Optional.empty();
		}
		Order orderBasket;
		try {
			if (orderBasketId == null) {
				orderBasketId = takeOrderBasketId(userId);
			}
			OrderProductConnection orderProductConnection = new OrderProductConnection(orderBasketId,
					Long.parseLong(productId), ONE_PRODUCT);
			if (!orderProductConnectionDao.increaseQuantityOfProduct(orderProductConnection)) {
				orderProductConnectionDao.create(orderProductConnection);
			}
			orderBasket = new Order(orderBasketId, userId);
		} catch (DaoException e) {
			throw new ServiceException("product adding error", e);
		}
		return Optional.of(orderBasket);
	}

	@Override
	public boolean changeQuantityOfProductInOrder(Long orderId, String productId, String quantityOfProduct)
			throws ServiceException {
		if (orderId == null || !IdValidator.isValidId(productId)
				|| !ProductInfoValidator.isValidQuantity(quantityOfProduct)) {
			return false;
		}
		boolean quantityOfProductChanged;
		OrderProductConnection orderProductConnection = new OrderProductConnection(orderId, Long.parseLong(productId),
				Integer.parseInt(quantityOfProduct));
		try {
			quantityOfProductChanged = orderProductConnectionDao.update(orderProductConnection);
		} catch (DaoException e) {
			throw new ServiceException("error changing quantity of product in order", e);
		}
		return quantityOfProductChanged;
	}

	@Override
	public boolean removeProductFromOrder(Long orderId, String productId) throws ServiceException {
		if (orderId == null || !IdValidator.isValidId(productId)) {
			return false;
		}
		OrderProductConnection orderProductConnection = new OrderProductConnection(orderId, Long.parseLong(productId));
		try {
			orderProductConnectionDao.delete(orderProductConnection);
		} catch (DaoException e) {
			throw new ServiceException("error removing a product from the order", e);
		}
		return true;
	}

	@Override
	public boolean checkout(Map<String, String> orderInfo) throws ServiceException, InvalidDataException {
		if (!IdValidator.isValidId(orderInfo.get(ParameterAndAttribute.ORDER_BASKET_ID))) {
			return false;
		}
		List<String> errorMessageList = OrderInfoValidator.findInvalidData(orderInfo);
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		Order order = OrderBuilder.getInstance().build(orderInfo);
		boolean orderPlaced;
		try {
			orderPlaced = orderDao.update(order);
			if (orderPlaced) {
				Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(order.getOrderId());
				productDao.reduceQuantity(products);
			}
		} catch (DaoException e) {
			throw new ServiceException("order updating error", e);
		}
		return orderPlaced;
	}

	@Override
	public boolean cancelOrder(String orderId, String orderStatus, UserRole userRole) throws ServiceException {
		if (!IdValidator.isValidId(orderId) || !OrderInfoValidator.isValidOrderStatus(orderStatus)
				|| userRole == null) {
			return false;
		}
		OrderStatus statusFrom = OrderStatus.valueOf(orderStatus.toUpperCase());
		if ((userRole == UserRole.CLIENT && statusFrom != OrderStatus.PLACED)
				|| (userRole == UserRole.ADMIN && statusFrom == OrderStatus.CANCELED)) {
			return false;
		}
		boolean orderCanceled;
		try {
			orderCanceled = orderDao.updateStatus(orderId, statusFrom, OrderStatus.CANCELED);
			if (orderCanceled) {
				Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(Long.parseLong(orderId));
				productDao.increaseQuantity(products);
			}
		} catch (DaoException e) {
			throw new ServiceException("orders changing status error", e);
		}
		return orderCanceled;
	}

	@Override
	public boolean processOrder(String orderId, String orderStatus) throws ServiceException {
		if (!IdValidator.isValidId(orderId) || !OrderInfoValidator.isValidOrderStatus(orderStatus)) {
			return false;
		}
		OrderStatus statusFrom = OrderStatus.valueOf(orderStatus.toUpperCase());
		OrderStatus statusTo;
		switch (statusFrom) {
		case PLACED:
			statusTo = OrderStatus.ACCEPTED;
			break;
		case ACCEPTED:
			statusTo = OrderStatus.READY;
			break;
		case READY:
			statusTo = OrderStatus.DELIVERED;
			break;
		default:
			return false;
		}
		boolean orderProcessed;
		try {
			orderProcessed = orderDao.updateStatus(orderId, statusFrom, statusTo);
		} catch (DaoException e) {
			throw new ServiceException("orders changing status error", e);
		}
		return orderProcessed;
	}

	@Override
	public Optional<Order> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException {
		if (userId == null) {
			return Optional.empty();
		}
		Order orderBasket;
		try {
			if (orderBasketId == null) {
				orderBasketId = takeOrderBasketId(userId);
			}
			orderBasket = new Order(orderBasketId, userId);
			Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(orderBasketId);
			orderBasket.setProducts(products);
			BigDecimal orderCost = PriceCalculator.calculateTotalCost(products);
			orderBasket.setCost(orderCost);
		} catch (DaoException e) {
			throw new ServiceException("product search error", e);
		}
		return Optional.of(orderBasket);
	}

	@Override
	public Optional<Order> takeOrderById(String orderId) throws ServiceException {
		if (!IdValidator.isValidId(orderId)) {
			return Optional.empty();
		}
		Optional<Order> orderOptional;
		try {
			orderOptional = orderDao.findOrderById(orderId);
			if (orderOptional.isPresent()) {
				Order order = orderOptional.get();
				Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(Long.parseLong(orderId));
				order.setProducts(products);
				if (order.getOrderStatus() == OrderStatus.BASKET) {
					order.setCost(PriceCalculator.calculateTotalCost(products));
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("order search error", e);
		}
		return orderOptional;
	}

	@Override
	public List<Order> takeOrdersByLogin(String login) throws ServiceException {
		if (!UserInfoValidator.isValidLogin(login)) {
			return Collections.emptyList();
		}
		List<Order> orders;
		try {
			orders = orderDao.findOrdersByLogin(login);
			if (!orders.isEmpty()) {
				Collections.reverse(orders);
				for (Order order : orders) {
					Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(order.getOrderId());
					order.setProducts(products);
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("orders search error", e);
		}
		return orders;
	}

	@Override
	public List<Order> takeOrdersByStatus(String orderStatus) throws ServiceException {
		logger.debug(orderStatus);
		if (!OrderInfoValidator.isValidOrderStatus(orderStatus)) {
			logger.debug(false);
			return Collections.emptyList();
		}
		List<Order> orders;
		try {
			orders = orderDao.findOrdersByStatus(orderStatus);
			if (!orders.isEmpty()) {
				Collections.reverse(orders);
				for (Order order : orders) {
					Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(order.getOrderId());
					order.setProducts(products);
					if (OrderStatus.valueOf(orderStatus.toUpperCase()) == OrderStatus.BASKET) {
						order.setCost(PriceCalculator.calculateTotalCost(products));
					}
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("orders search error", e);
		}
		return orders;
	}

	private Long takeOrderBasketId(Long userId) throws DaoException {
		Long orderBasketId;
		Optional<Long> orderBasketIdOptional = orderDao.findOrderBasketId(userId);
		if (orderBasketIdOptional.isPresent()) {
			orderBasketId = orderBasketIdOptional.get();
		} else {
			Order order = new Order(userId);
			orderDao.create(order);
			orderBasketId = order.getOrderId();
		}
		return orderBasketId;
	}
}