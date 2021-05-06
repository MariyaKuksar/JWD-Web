package by.epam.store.model.service;

import java.util.Map;

import by.epam.store.model.entity.Basket;

public interface OrderService {

	Long addProductToBasket(Long userId, Long orderBasketId, String productId) throws ServiceException;

	Basket takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException;

	boolean changeAmountOfProductInOrder(Long orderId, String productId, String amountProduct) throws ServiceException;

	void removeProductFromOrder(Long orderId, String productId) throws ServiceException;

	void checkout(Map<String, String> orderInfo) throws ServiceException, InvalidDataException;
}
