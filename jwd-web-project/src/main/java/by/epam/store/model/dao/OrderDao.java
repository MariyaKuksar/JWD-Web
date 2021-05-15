package by.epam.store.model.dao;

import java.util.List;
import java.util.Optional;

import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;

public interface OrderDao extends BaseDao<Order> {

	Optional<Long> findOrderBasketId(Long userId) throws DaoException;

	List<Order> findOrdersByUserId(Long userId) throws DaoException;

	boolean updateStatus(String orderId, OrderStatus orderStatus) throws DaoException;

	List<Order> findOrdersByStatus(String orderStatus) throws DaoException;
}
