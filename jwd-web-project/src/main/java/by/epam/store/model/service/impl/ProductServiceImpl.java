package by.epam.store.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.impl.ProductCategoryDaoImpl;
import by.epam.store.model.dao.impl.ProductDaoImpl;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.model.service.ProductService;
import by.epam.store.model.service.ServiceException;

public class ProductServiceImpl implements ProductService  {
	private static final Logger logger = LogManager.getLogger();
	private ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl();
	private ProductDao productDao = new ProductDaoImpl();

	@Override
	public List<ProductCategory> findAllProductCategories() throws ServiceException {
		List<ProductCategory> productCategoties;
		try {
			productCategoties = productCategoryDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("product categories search error", e);
		}
		return productCategoties;
	}

	@Override
	public List<Product> findProductsFromCategory(String categoryId) throws ServiceException {
		List<Product> products;
		try {
			Long id = Long.parseLong(categoryId);
			products = productDao.findProductByCategoryId(id);
		} catch (NumberFormatException e) {
			logger.info("categoryId incorrect");
			products = Collections.emptyList();
		} catch (DaoException e) {
			throw new ServiceException("products from category search error", e);
		}
		return products;
	}
}
