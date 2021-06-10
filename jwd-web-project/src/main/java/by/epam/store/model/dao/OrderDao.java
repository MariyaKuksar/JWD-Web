package by.epam.store.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.model.connection.ConnectionPoolException;

/**
 * The interface for working with database table orders
 * 
 * @author Mariya Kuksar
 * @see BaseDao
 */
public interface OrderDao extends BaseDao<Order> {

	/**
	 * Updates order status
	 * 
	 * @param orderId    {@link String} order id
	 * @param statusFrom {@link OrderStatus} current order status
	 * @param statusTo   {@link OrderStatus} new order status
	 * @return boolean true if the status has been updated, else false
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	boolean updateStatus(String orderId, OrderStatus statusFrom, OrderStatus statusTo) throws DaoException;

	/**
	 * Looking for an order where the status is basket
	 * 
	 * @param userId {@link Long} user id
	 * @return {@link Optional} of {@link Long} order basket id received from
	 *         database
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Optional<Long> findOrderBasketId(Long userId) throws DaoException;

	/**
	 * Looking for an order by id
	 * 
	 * @param orderId {@link String} order id
	 * @return {@link Optional} of {@link Order} received from database
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Optional<Order> findOrderById(String orderId) throws DaoException;

	/**
	 * Looking for orders by user login
	 * 
	 * @param login {@link String} user login
	 * @return {@link List} of {@link Order} received from database if orders are
	 *         found, else emptyList
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	List<Order> findOrdersByLogin(String login) throws DaoException;

	/**
	 * Looking for orders by status
	 * 
	 * @param orderStatus {@link String} order status
	 * @return {@link List} of {@link Order} received from database if orders are
	 *         found, else emptyList
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	List<Order> findOrdersByStatus(String orderStatus) throws DaoException;
}
