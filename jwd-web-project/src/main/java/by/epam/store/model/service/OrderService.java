package by.epam.store.model.service;

import java.util.Optional;

public interface OrderService {

	Optional<Long> addProduct(Long userId, Long orderBascetId, String productId) throws ServiceException;

}
