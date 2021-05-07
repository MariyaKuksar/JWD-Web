package by.epam.store.model.dao;

import java.util.Optional;

import by.epam.store.entity.Order;

public interface OrderDao extends BaseDao<Order> {

	Optional<Long> findOrderBasketId(Long userId) throws DaoException;
}
