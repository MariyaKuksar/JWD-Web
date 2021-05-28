package by.epam.store.model.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductList;

public interface ProductDao extends BaseDao<Product> {

	void increaseQuantity(Map<Product, Integer> products) throws DaoException;

	void reduceQuantity(Map<Product, Integer> products) throws DaoException;

	List<Product> findProductsByCategoryId(String categoryId) throws DaoException;
	
	Optional<Product> findProductById(String productId) throws DaoException;

	List<Product> findProductsByName(String productName) throws DaoException;

	ProductList findProductsInStock(int start, int recordsPerPages) throws DaoException;

	ProductList findProductsOnOrder(int start, int recordsPerPages) throws DaoException;
}
