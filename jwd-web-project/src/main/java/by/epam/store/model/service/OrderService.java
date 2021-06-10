package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Order;
import by.epam.store.entity.UserRole;
import by.epam.store.model.dao.DaoException;

/**
 * The interface for operations with the orders
 * 
 * @author Mariya Kuksar
 */
public interface OrderService {

	/**
	 * Adds a product to the basket
	 * 
	 * @param userId        {@link Long} user id
	 * @param orderBasketId {@link Long} order id with basket status
	 * @param productId     {@link String} product id
	 * @return {@link Optional} of {@link Order} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<Order> addProductToBasket(Long userId, Long orderBasketId, String productId) throws ServiceException;

	/**
	 * Changes the quantity of product in the order
	 * 
	 * @param orderId           {@link Long} order id
	 * @param productId         {@link String} product id
	 * @param quantityOfProduct {@link String} the quantity of product
	 * @return boolean true if the quantity of product has been changed, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean changeQuantityOfProductInOrder(Long orderId, String productId, String quantityOfProduct)
			throws ServiceException;

	/**
	 * Removes a product from an order
	 * 
	 * @param orderId   {@link Long} order id
	 * @param productId {@link String} product id
	 * @return boolean true if the product has been removed, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean removeProductFromOrder(Long orderId, String productId) throws ServiceException;

	/**
	 * Makes an order
	 * 
	 * @param orderInfo {@link Map} of {@link String} and {@link String} the names
	 *                  of the {@link Order} fields and its values
	 * @return boolean true if the order has been placed, else false
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if order info is invalid
	 */
	boolean checkout(Map<String, String> orderInfo) throws ServiceException, InvalidDataException;

	/**
	 * Cancels order
	 * 
	 * @param orderId     {@link String} order id
	 * @param orderStatus {@link String} current order status
	 * @param role        {@link UserRole} the role of the user who cancels the
	 *                    order
	 * @return boolean true if the order has been canceled, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean cancelOrder(String orderId, String orderStatus, UserRole role) throws ServiceException;

	/**
	 * Processes an order
	 * 
	 * @param orderId     {@link String} order id
	 * @param orderStatus {@link String} current order status
	 * @return boolean true if the order has been processed, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean processOrder(String orderId, String orderStatus) throws ServiceException;

	/**
	 * Gives an order with the status basket
	 * 
	 * @param userId        {@link Long} user id
	 * @param orderBasketId {@link Long} order basket id
	 * @return {@link Optional} of {@link Order} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<Order> takeOrderBasket(Long userId, Long orderBasketId) throws ServiceException;

	/**
	 * Gives an order by id
	 * 
	 * @param orderId {@link String} order id
	 * @return {@link Optional} of {@link Order} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<Order> takeOrderById(String orderId) throws ServiceException;

	/**
	 * Gives orders by user login
	 * 
	 * @param login {@link String} user login
	 * @return {@link List} of {@link Order} received from database if orders are
	 *         found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<Order> takeOrdersByLogin(String login) throws ServiceException;

	/**
	 * Gives orders by status
	 * 
	 * @param orderStatus {@link String} order status
	 * @return {@link List} of {@link Order} received from database if orders are
	 *         found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<Order> takeOrdersByStatus(String orderStatus) throws ServiceException;
}
