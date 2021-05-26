package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.ProductList;

public interface CatalogService {

	void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	boolean acceptProducts(Map<Product, Integer> suppliedProducts) throws ServiceException;

	boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	List<ProductCategory> takeAllProductCategories() throws ServiceException;

	Optional<Product> takeProductById(String productId) throws ServiceException;

	List<Product> takeProductsFromCategory(String categoryId, String sortingMethod) throws ServiceException;

	List<Product> takeProductsWithName(String productName, String sortingMethod) throws ServiceException;

	ProductList takeProductsInStock(String page) throws ServiceException;

	ProductList takeProductsOnOrder(String page) throws ServiceException;
}
