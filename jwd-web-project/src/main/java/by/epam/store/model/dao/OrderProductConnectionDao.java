package by.epam.store.model.dao;

import java.sql.SQLException;
import java.util.Map;

import by.epam.store.entity.OrderProductConnection;
import by.epam.store.entity.Product;
import by.epam.store.model.connection.ConnectionPoolException;

/**
 * The interface for working with database table order_product_connections
 * 
 * @author Mariya Kuksar
 * @see BaseDao
 */
public interface OrderProductConnectionDao extends BaseDao<OrderProductConnection> {

	/**
	 * Increases quantity of products
	 * 
	 * @param orderProductConnection {@link OrderProductConnection} all data to
	 *                               increase
	 * @return boolean true if the quantity of product has been increased, else
	 *         false
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	boolean increaseQuantityOfProduct(OrderProductConnection orderProductConnection) throws DaoException;

	/**
	 * Deletes order product connection
	 * 
	 * @param orderProductConnection {@link OrderProductConnection} all data to
	 *                               delete
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	void delete(OrderProductConnection orderProductConnection) throws DaoException;

	/**
	 * Looking for products and its quantity by order id
	 * 
	 * @param orderId {@link Long} order id
	 * @return {@link Map} of {@link Product} and {@link Integer} products and its
	 *         quantity
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Map<Product, Integer> findByOrderId(Long orderId) throws DaoException;
}
