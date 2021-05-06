package by.epam.store.model.dao;

import java.util.Map;

import by.epam.store.model.entity.OrderProductConnection;
import by.epam.store.model.entity.Product;

public interface OrderProductConnectionDao extends BaseDao<OrderProductConnection> {

	boolean increaseAmountOfProduct(OrderProductConnection orderProductConnection) throws DaoException;

	Map<Product, Integer> findByOrderId(Long orderId) throws DaoException;

	void delete(OrderProductConnection orderProductConnection) throws DaoException;
}
