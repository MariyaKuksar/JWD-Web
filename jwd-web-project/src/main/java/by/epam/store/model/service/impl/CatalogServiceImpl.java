package by.epam.store.model.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.builder.ProductBuilder;
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
	public List<Product> takeProductsFromCategory(String categoryId) throws ServiceException {
		if(!IdValidator.isValidId(categoryId)) {
			return Collections.emptyList();
		}
		List<Product> products;
		try {
			products = productDao.findProductsByCategoryId(categoryId);
		} catch (DaoException e) {
			throw new ServiceException("products from category search error", e);
		}
		return products;
	}

	@Override
	public List<Product> takeProductsWithName(String productName) throws ServiceException {
		if (!ProductInfoValidator.isValidName(productName)) {
			return Collections.emptyList();
		}
		List<Product> products;
		try {
			products = productDao.findProductsByName(productName);
		} catch (DaoException e) {
			throw new ServiceException("products search error", e);
		}
		return products;
	}

	@Override
	public void addProduct(Map<String, String> productInfo) throws ServiceException, InvalidDataException {
		List<String> errorMessageList = ProductInfoValidator.findInvalidData(productInfo);
		if (!IdValidator.isValidId(productInfo.get(ParameterAndAttribute.CATEGORY_ID))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_CATEGORY_MESSAGE);
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
}
