package by.epam.store.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductList;
import by.epam.store.model.connection.ConnectionPoolException;

/**
 * The interface for working with database table products
 * 
 * @author Mariya Kuksar
 * @see BaseDao
 */
public interface ProductDao extends BaseDao<Product> {

	/**
	 * Increases quantity of products
	 * 
	 * @param products {@link Map} of {@link Product} and {@link Integer} products
	 *                 and their quantity to increase
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	void increaseQuantity(Map<Product, Integer> products) throws DaoException;

	/**
	 * Reduces quantity of products
	 * 
	 * @param products {@link Map} of {@link Product} and {@link Integer} products
	 *                 and their quantity to reduce
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	void reduceQuantity(Map<Product, Integer> products) throws DaoException;

	/**
	 * Looking for products by category id
	 * 
	 * @param categoryId {@link String} category id
	 * @return {@link List} of {@link Product} received from database if products
	 *         are found, else emptyList
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	List<Product> findProductsByCategoryId(String categoryId) throws DaoException;

	/**
	 * Looking for product by id
	 * 
	 * @param productId {@link String} product id
	 * @return {@link Optional} of {@link Product} received from database
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Optional<Product> findProductById(String productId) throws DaoException;

	/**
	 * Looking for products by name
	 * 
	 * @param productName {@link String} product name
	 * @return {@link List} of {@link Product} received from database if products
	 *         are found, else emptyList
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	List<Product> findProductsByName(String productName) throws DaoException;

	/**
	 * Looking for products in stock
	 * 
	 * @param startPosition   start position product in database
	 * @param recordsPerPages quantity of getting products
	 * @return {@link ProductList} getting products and information for pagination
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	ProductList findProductsInStock(int startPosition, int recordsPerPages) throws DaoException;

	/**
	 * Looking for products on order
	 * 
	 * @param startPosition   start position product in database
	 * @param recordsPerPages quantity of getting products
	 * @return {@link ProductList} getting products and information for pagination
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	ProductList findProductsOnOrder(int startPosition, int recordsPerPages) throws DaoException;
}
