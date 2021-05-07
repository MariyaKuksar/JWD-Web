package by.epam.store.model.service;

import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;

public interface CatalogService {

	List<ProductCategory> takeAllProductCategories() throws ServiceException;

	List<Product> takeProductsFromCategory (String categoryId) throws ServiceException;

	List<Product> takeProductsWithName(String productName) throws ServiceException;
	
	void addProduct (Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	void changeProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException;
}
