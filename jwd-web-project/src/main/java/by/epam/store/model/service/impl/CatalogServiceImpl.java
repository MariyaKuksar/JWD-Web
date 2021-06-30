package by.epam.store.model.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.ProductList;
import by.epam.store.entity.Supply;
import by.epam.store.entity.builder.impl.ProductBuilder;
import by.epam.store.entity.comparator.ProductComparator;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.SupplyDao;
import by.epam.store.model.dao.impl.ProductCategoryDaoImpl;
import by.epam.store.model.dao.impl.ProductDaoImpl;
import by.epam.store.model.dao.impl.SupplyDaoImpl;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.validator.IdValidator;
import by.epam.store.model.service.validator.ProductInfoValidator;
import by.epam.store.util.MessageKey;

/**
 * The service is responsible for operations with the product catalog
 * 
 * @author Mariya Kuksar
 * @see CatalogService
 */
public class CatalogServiceImpl implements CatalogService {
	private static final Logger logger = LogManager.getLogger();
	private static final int RECORDS_PER_PAGES = 4;
	private static final int DEFAULT_PAGE_NUMBER = 1;
	private ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();
	private ProductDao productDao = new ProductDaoImpl();
	private SupplyDao supplyDao = new SupplyDaoImpl();

	@Override
	public void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = ProductInfoValidator.findInvalidData(productInfo);
		if (productInfo != null && !IdValidator.isValidId(productInfo.get(ParameterAndAttribute.CATEGORY_ID))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_CATEGORY_MESSAGE);
		}
		if (productInfo != null
				&& !ProductInfoValidator.isValidImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_IMAGE_NAME_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		Product product = ProductBuilder.getInstance().build(productInfo);
		try {
			productDao.create(product);
		} catch (DaoException e) {
			throw new ServiceException("product creation error", e);
		}
	}

	@Override
	public boolean acceptProducts(Map<Product, Integer> suppliedProducts) throws ServiceException {
		if (MapUtils.isEmpty(suppliedProducts)) {
			return false;
		}
		Supply supply = new Supply(LocalDateTime.now(), suppliedProducts);
		try {
			supplyDao.create(supply);
			supplyDao.createSupplyProductConnection(supply);
			productDao.increaseQuantity(suppliedProducts);
		} catch (DaoException e) {
			throw new ServiceException("product accepting error", e);
		}
		return true;
	}

	@Override
	public boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = ProductInfoValidator.findInvalidData(productInfo);
		if (productInfo != null && !IdValidator.isValidId(productInfo.get(ParameterAndAttribute.PRODUCT_ID))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_ID_MESSAGE);
		}
		if (!errorMessageList.isEmpty()) {
			throw new InvalidDataException("invalid data", errorMessageList);
		}
		Product product = ProductBuilder.getInstance().build(productInfo);
		boolean productChanged;
		try {
			productChanged = productDao.update(product);
		} catch (DaoException e) {
			throw new ServiceException("product changing error", e);
		}
		return productChanged;
	}

	@Override
	public List<ProductCategory> takeAllProductCategories() throws ServiceException {
		List<ProductCategory> productCategories;
		try {
			productCategories = productCategoryDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("product categories search error", e);
		}
		return productCategories;
	}

	@Override
	public Optional<Product> takeProductById(String productId) throws ServiceException {
		if (!IdValidator.isValidId(productId)) {
			return Optional.empty();
		}
		Optional<Product> productOptional;
		try {
			productOptional = productDao.findProductById(productId);
		} catch (DaoException e) {
			throw new ServiceException("product search error", e);
		}
		return productOptional;
	}

	@Override
	public List<Product> takeProductsFromCategory(String categoryId, String sortingMethod) throws ServiceException {
		if (!IdValidator.isValidId(categoryId)) {
			return Collections.emptyList();
		}
		List<Product> products = new ArrayList<>();
		try {
			products = productDao.findProductsByCategoryId(categoryId);
			if (!products.isEmpty() && sortingMethod != null) {
				products.sort(ProductComparator.valueOf(sortingMethod.toUpperCase()).getComporator());
			}
		} catch (DaoException e) {
			throw new ServiceException("products from category search error", e);
		} catch (IllegalArgumentException e) {
			logger.error("impossible sorting method", e);
		}
		return products;
	}

	@Override
	public List<Product> takeProductsWithName(String productName, String sortingMethod) throws ServiceException {
		if (!ProductInfoValidator.isValidName(productName)) {
			return Collections.emptyList();
		}
		List<Product> products = new ArrayList<>();
		try {
			products = productDao.findProductsByName(productName);
			if (!products.isEmpty() && sortingMethod != null) {
				products.sort(ProductComparator.valueOf(sortingMethod.toUpperCase()).getComporator());
			}
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		} catch (IllegalArgumentException e) {
			logger.error("impossible sorting method", e);
		}
		return products;
	}

	@Override
	public ProductList takeProductsInStock(String page) throws ServiceException {
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		int startPozition = pageNumber * RECORDS_PER_PAGES - RECORDS_PER_PAGES;
		ProductList productList;
		try {
			productList = productDao.findProductsInStock(startPozition, RECORDS_PER_PAGES);
			productList.setCurrentPageNumber(pageNumber);
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		}
		return productList;
	}

	@Override
	public ProductList takeProductsOnOrder(String page) throws ServiceException {
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		int startPozition = pageNumber * RECORDS_PER_PAGES - RECORDS_PER_PAGES;
		ProductList productList;
		try {
			productList = productDao.findProductsOnOrder(startPozition, RECORDS_PER_PAGES);
			productList.setCurrentPageNumber(pageNumber);
			List<Product> products = productList.getProducts();
			if (!products.isEmpty()) {
				products.forEach(product -> product.setQuantity(Math.abs(product.getQuantity())));
			}
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		}
		return productList;
	}
}
