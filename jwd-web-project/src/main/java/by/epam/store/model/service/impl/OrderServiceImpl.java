package by.epam.store.model.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.dao.impl.OrderDaoImpl;
import by.epam.store.model.dao.impl.OrderProductConnectionDaoImpl;
import by.epam.store.model.entity.Basket;
import by.epam.store.model.entity.DeliveryMethod;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.OrderProductConnection;
import by.epam.store.model.entity.PaymentMethod;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.builder.OrderBuilder;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.ServiceException;
import by.epam.store.util.PriceCalculator;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.OrderInfoValidator;
import by.epam.store.validator.ProductInfoValidator;

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
			if (!orderProductConnectionDao.increaseAmountOfProduct(orderProductConnection)) {
				orderProductConnectionDao.create(orderProductConnection);
			}
		} catch (DaoException e) {
			throw new ServiceException("product adding error", e);
		}
		return Optional.of(orderBasketId);
	}

	@Override
	public Optional<Basket> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException {
		if (userId == null) {
			return Optional.empty();
		}
		Basket basket;
		try {
			if (orderBasketId == null) {
				orderBasketId = takeOrderBasketId(userId);
			}
			basket = new Basket(orderBasketId, userId);
			Map<Product, Integer> products = orderProductConnectionDao.findByOrderId(orderBasketId);
			basket.setProducts(products);
			BigDecimal orderCost = PriceCalculator.calculateTotalCost(products);
			basket.setCost(orderCost);
			basket.setDeliveryMethodList(Arrays.asList(DeliveryMethod.values()));
			basket.setPaymentMethodList(Arrays.asList(PaymentMethod.values()));
		} catch (DaoException e) {
			throw new ServiceException("product search error", e);
		}
		return Optional.of(basket);
	}

	@Override
	public boolean changeAmountOfProductInOrder(Long orderId, String productId, String amountProduct)
			throws ServiceException {
		if (orderId == null || !IdValidator.isValidId(productId)
				|| !ProductInfoValidator.isValidAmount(amountProduct)) {
			return false;
		}
		boolean amountOfProductChanged;
		OrderProductConnection orderProductConnection = new OrderProductConnection(orderId, Long.parseLong(productId),
				Integer.parseInt(amountProduct));
		try {
			amountOfProductChanged = orderProductConnectionDao.update(orderProductConnection);
		} catch (DaoException e) {
			throw new ServiceException("error changing amount of product in order", e);
		}
		return amountOfProductChanged;
	}

	@Override
	public boolean removeProductFromOrder(Long orderId, String productId) throws ServiceException {
		if (orderId == null || !IdValidator.isValidId(productId)) {
			return false;
		}
		boolean productRemoved;
		OrderProductConnection orderProductConnection = new OrderProductConnection(orderId, Long.parseLong(productId));
		try {
			productRemoved = orderProductConnectionDao.delete(orderProductConnection);
		} catch (DaoException e) {
			throw new ServiceException("error removing a product from the order", e);
		}
		return productRemoved;
	}

	@Override
	public void checkout(Map<String, String> orderInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = OrderInfoValidator.findInvalidData(orderInfo);
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		Order order = OrderBuilder.getInstance().build(orderInfo);
		logger.debug(order.toString());
		try {
			orderDao.update(order);
		} catch (DaoException e) {
			throw new ServiceException("order updating error", e);
		}
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