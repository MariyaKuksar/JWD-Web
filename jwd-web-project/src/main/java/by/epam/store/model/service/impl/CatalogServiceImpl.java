package by.epam.store.model.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.impl.ProductCategoryDaoImpl;
import by.epam.store.model.dao.impl.ProductDaoImpl;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.model.entity.builder.ProductBuilder;
import by.epam.store.model.service.CatalogService;
import by.epam.store.model.service.ServiceException;
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
	public List<Product> takeProductsFromCategory(String categoryId) throws ServiceException {
		if (!IdValidator.isValidId(categoryId)) {
			return Collections.emptyList();
		}
		List<Product> products;
		try {
			products = productDao.findProductByCategoryId(categoryId);
		} catch (DaoException e) {
			throw new ServiceException("products from category search error", e);
		}
		return products;
	}

	@Override
	public List<Product> takeProductsWithName(String productName) throws ServiceException {
		if (productName == null) {
			logger.debug("product name is null");
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
	public List<String> addProduct(Map<String, String> productInfo) throws ServiceException {
		List<String> errorMessageList = ProductInfoValidator.getErrorMessageList(productInfo);
		if (!errorMessageList.isEmpty()) {
			return errorMessageList;
		}
		Product product = ProductBuilder.getInstance().build(productInfo);
		try {
			productDao.create(product);
		} catch (DaoException e) {
			throw new ServiceException("product creation error", e);
		}
		return errorMessageList;
	}
}
