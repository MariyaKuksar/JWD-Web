package by.epam.store.model.dao;

import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;

public interface ProductDao extends BaseDao<Product> {
	
	List<Product> findProductsByCategoryId(String categoryId) throws DaoException;

	List<Product> findProductsByName(String productName) throws DaoException;

	void reduceAmount(Map<Product, Integer> products) throws DaoException;
}
