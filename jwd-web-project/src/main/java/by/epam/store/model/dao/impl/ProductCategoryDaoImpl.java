package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.model.entity.builder.ProductCategoryBuilder;

public class ProductCategoryDaoImpl implements ProductCategoryDao {
	//private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_ALL_PRODUCT_CATEGORIES = "SELECT ID, CATEGORY, IMAGE_NAME FROM PRODUCT_CATEGORIES";
	
	@Override
	public List<ProductCategory> findAll() throws DaoException {
		List<ProductCategory> productCategories;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRODUCT_CATEGORIES);
			productCategories = ProductCategoryBuilder.getInstance().build(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return productCategories;
	}

	@Override
	public Long create(ProductCategory entity) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ProductCategory entity) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

}