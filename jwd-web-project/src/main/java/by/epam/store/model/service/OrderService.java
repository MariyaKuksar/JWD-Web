package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Order;

public interface OrderService {

	Optional<Order> addProductToBasket(Long userId, Long orderBasketId, String productId) throws ServiceException;

	Optional<Order> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException;

	boolean changeAmountOfProductInOrder(Long orderId, String productId, String amountProduct) throws ServiceException;

	boolean removeProductFromOrder(Long orderId, String productId) throws ServiceException;

	boolean checkout(Map<String, String> orderInfo) throws ServiceException, InvalidDataException;

	List<Order> takeOrdersByUserId(Long userId) throws ServiceException;

	boolean cancelOrder(String orderId) throws ServiceException;

	List<Order> takeOrdersByStatus(String orderStatus) throws ServiceException;
}
