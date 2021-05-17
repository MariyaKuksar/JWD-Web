package by.epam.store.model.dao;

import java.util.List;
import java.util.Optional;

import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;

public interface OrderDao extends BaseDao<Order> {

	Optional<Long> findOrderBasketId(Long userId) throws DaoException;

	List<Order> findOrdersByLogin(String login) throws DaoException;

	boolean updateStatus(String orderId, OrderStatus statusFrom, OrderStatus statusTo) throws DaoException;

	List<Order> findOrdersByStatus(String orderStatus) throws DaoException;

	Optional<Order> findOrderById(String orderId) throws DaoException;
}
