package by.epam.store.model.dao;

import java.util.List;
import java.util.Optional;

import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;

public interface OrderDao extends BaseDao<Order> {
	
	boolean updateStatus(String orderId, OrderStatus statusFrom, OrderStatus statusTo) throws DaoException;

	Optional<Long> findOrderBasketId(Long userId) throws DaoException;
	
	Optional<Order> findOrderById(String orderId) throws DaoException;

	List<Order> findOrdersByLogin(String login) throws DaoException;

	List<Order> findOrdersByStatus(String orderStatus) throws DaoException;
}
