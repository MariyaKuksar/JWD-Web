package by.epam.store.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.builder.ProductBuilder;
import by.epam.store.entity.comparator.ProductComparator;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.impl.ProductCategoryDaoImpl;
import by.epam.store.model.dao.impl.ProductDaoImpl;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.validator.IdValidator;
import by.epam.store.validator.ProductInfoValidator;

public class CatalogServiceImpl implements CatalogService {
	private static final Logger logger = LogManager.getLogger();
	private ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();
	private ProductDao productDao = new ProductDaoImpl();

	@Override
	public List<ProductCategory> takeAllProductCategories() throws ServiceException {
		List<ProductCategory> productCategoties;
		try {
			productCategoties = productCategoryDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("product categories search error", e);
		}
		return productCategoties;
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
			logger.error("impossible sorting method");
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
			logger.error("impossible sorting method");
		}
		return products;
	}

	@Override
	public void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = ProductInfoValidator.findInvalidData(productInfo);
		if (!IdValidator.isValidId(productInfo.get(ParameterAndAttribute.CATEGORY_ID))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_CATEGORY_MESSAGE);
		}
		if (!ProductInfoValidator.isValidImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME))) {
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
	public boolean changeProductData(Map<String, String> productInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = ProductInfoValidator.findInvalidData(productInfo);
		if (!IdValidator.isValidId(productInfo.get(ParameterAndAttribute.PRODUCT_ID))) {
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
	public List<Product> takeProductsInStock() throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = productDao.findProductsInStock();
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		}
		return products;
	}

	@Override
	public List<Product> takeProductsOnOrder() throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = productDao.findProductsOnOrder();
			if (!products.isEmpty()) {
				for (Product product : products) {
					product.setAmount(Math.abs(product.getAmount()));
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		}
		return products;
	}
}
