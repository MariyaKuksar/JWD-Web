package by.epam.store.model.dao;

import java.util.List;

import by.epam.store.entity.Product;

public interface ProductDao extends BaseDao<Product> {
	
	List<Product> findProductByCategoryId(Long id) throws DaoException;

	List<Product> findProductsByName(String productName) throws DaoException;
}
