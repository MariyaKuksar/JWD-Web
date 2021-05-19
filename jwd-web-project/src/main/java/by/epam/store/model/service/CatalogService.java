package by.epam.store.model.service;

import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;

public interface CatalogService {

	List<ProductCategory> takeAllProductCategories() throws ServiceException;

	List<Product> takeProductsFromCategory(String categoryId, String sortingMethod) throws ServiceException;

	List<Product> takeProductsWithName(String productName, String sortingMethod) throws ServiceException;

	void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	List<Product> takeProductsInStock() throws ServiceException;

	List<Product> takeProductsOnOrder() throws ServiceException;
}
