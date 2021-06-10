package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.ProductList;
import by.epam.store.model.dao.DaoException;

/**
 * The interface for operations with the product catalog
 * 
 * @author Mariya Kuksar
 */
public interface CatalogService {

	/**
	 * Adds a new product to the catalog
	 * 
	 * @param productInfo {@link Map} of {@link String} and {@link String} the names
	 *                    of the {@link Product} fields and its values
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if product info is invalid
	 */
	void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	/**
	 * Accepts supplied products
	 * 
	 * @param suppliedProducts {@link Map} of {@link Product} and {@link Integer}
	 *                         supplied products and its quantity
	 * @return boolean true if products have been accepted, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean acceptProducts(Map<Product, Integer> suppliedProducts) throws ServiceException;

	/**
	 * Changes product data
	 * 
	 * @param productInfo {@link Map} of {@link String} and {@link String} the names
	 *                    of the {@link Product} fields and its values
	 * @return boolean true if product data has been changed, else false
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if product info is invalid
	 */
	boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException;

	/**
	 * Gives all product categories
	 * 
	 * @return {@link List} of {@link ProductCategory} received from database if
	 *         products are found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<ProductCategory> takeAllProductCategories() throws ServiceException;

	/**
	 * Gives a product by id
	 * 
	 * @param productId {@link String} product id
	 * @return {@link Optional} of {@link Product} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<Product> takeProductById(String productId) throws ServiceException;

	/**
	 * Gives products from category
	 * 
	 * @param categoryId    {@link String} category id
	 * @param sortingMethod {@link String} a way to sort products, can be null if
	 *                      sorting is not needed
	 * @return {@link List} of {@link Product} received from database and sorted
	 *         when there is a sort method if products are found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<Product> takeProductsFromCategory(String categoryId, String sortingMethod) throws ServiceException;

	/**
	 * Gives products by the name
	 * 
	 * @param productName   {@link String} product name
	 * @param sortingMethod {@link String} a way to sort products, can be null if
	 *                      sorting is not needed
	 * @return {@link List} of {@link Product} received from database and sorted
	 *         when there is a sort method if products are found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<Product> takeProductsWithName(String productName, String sortingMethod) throws ServiceException;

	/**
	 * Gives products in stock
	 * 
	 * @param page {@link String} page number
	 * @return {@link ProductList} received from database if products are found,
	 *         else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	ProductList takeProductsInStock(String page) throws ServiceException;

	/**
	 * Gives products on order
	 * 
	 * @param page {@link String} page number
	 * @return {@link ProductList} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	ProductList takeProductsOnOrder(String page) throws ServiceException;
}
