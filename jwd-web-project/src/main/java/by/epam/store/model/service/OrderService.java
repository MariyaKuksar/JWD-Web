package by.epam.store.model.service;

import java.util.Optional;

import by.epam.store.model.entity.Order;

public interface OrderService {
	
	Optional<Long> addProductToBasket (Long userId, Long orderBasketId, String productId) throws ServiceException;

	Optional<Order> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException;
}
