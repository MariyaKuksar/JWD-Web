package by.epam.store.model.dao;

import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductList;

public interface ProductDao extends BaseDao<Product> {

	List<Product> findProductsByCategoryId(String categoryId) throws DaoException;

	List<Product> findProductsByName(String productName) throws DaoException;

	void reduceAmount(Map<Product, Integer> products) throws DaoException;

	void increaseAmount(Map<Product, Integer> products) throws DaoException;

	ProductList findProductsInStock(int start, int recordsPerPages) throws DaoException;

	ProductList findProductsOnOrder(int start, int recordsPerPages) throws DaoException;
}
