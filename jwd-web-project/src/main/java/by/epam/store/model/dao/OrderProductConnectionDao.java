package by.epam.store.model.dao;

import java.util.Map;

import by.epam.store.model.entity.OrderProductConnection;
import by.epam.store.model.entity.Product;

public interface OrderProductConnectionDao extends BaseDao<OrderProductConnection> {
	
	Map<Product, Integer> findByOrderId(Long orderId) throws DaoException;
}
