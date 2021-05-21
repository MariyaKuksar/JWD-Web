package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.ProductList;

public interface CatalogService {

	List<ProductCategory> takeAllProductCategories() throws ServiceException;

	List<Product> takeProductsFromCategory(String categoryId, String sortingMethod) throws ServiceException;

	List<Product> takeProductsWithName(String productName, String sortingMethod) throws ServiceException;

	void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	ProductList takeProductsInStock(String page) throws ServiceException;

	ProductList takeProductsOnOrder(String page) throws ServiceException;

	Optional<Product> takeProductById(String productId) throws ServiceException;
}
